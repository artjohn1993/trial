package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.array.PublicArray
import com.example.artjohn.blackfin.event.*
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider
import org.greenrobot.eventbus.EventBus

class RedundancyDialog {
    var dialog : Dialog? = null
    var closeButton : ImageView? = null
    var coverAmount : EditText? = null
    var loading : Spinner? = null
    var index : Switch? = null
    var applyButton : Button? = null
    var remove : Button? = null
    var coverAmountVal : Double = 0.0
    var loadingVal : Double = 0.0

    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        dialog = Dialog(activity)
        setupDialog(dialog)
        dialogViewId(dialog)
        setAdapter(activity)
        setConfiguredBenefits(id)
        closeButton?.setOnClickListener {
            dialog?.hide()
        }
        remove?.setOnClickListener {
            dialog?.hide()
            EventBus.getDefault().post(RemoveConfiguredBenefits(id,8))
        }
        applyButton?.setOnClickListener {
            if (!coverAmount?.text.isNullOrEmpty()) {
                dialog?.hide()
                var benefitsProduct = ProcessProduct().getListProduct(product,
                        provider,
                        8)
                coverAmountVal = Conversion.coverAmount(coverAmount?.text.toString())
                loadingVal = Conversion.loading(loading?.selectedItem.toString())

                ConfiguredBenefits(false,
                        false,
                        0,
                        1,
                        false,
                        false,
                        12,
                        false,
                        false,
                        "Term",
                        "AnyOccupation",
                        0,
                        false,
                        false,
                        0,
                        coverAmountVal,
                        loadingVal,
                        false,
                        benefitsProduct,
                        "Trauma Cover",
                        id,
                        8)
            }
        }

        dialog?.show()
    }
    private fun setupDialog(customDialog: Dialog?) {
        dialog?.setContentView(R.layout.layout_redundancy)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
    }
    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById(R.id.closeButton)
        coverAmount = dialog?.findViewById(R.id.coverAmountEdit)
        loading = dialog?.findViewById(R.id.loadingSpinner)
        index = dialog?.findViewById(R.id.indexedSwitch)
        applyButton = dialog?.findViewById(R.id.applyButton)
        remove = dialog?.findViewById(R.id.removeButton)
    }
    private fun setConfiguredBenefits(id : Int) {
        for (x in 0 until ConfigureBenefits.array.size) {
            if (ConfigureBenefits.array[x].clientId == id && ConfigureBenefits.array[x].inputs.benefitProductList[0].benefitId == 8) {
                coverAmount?.setText(ConfigureBenefits.array[x].inputs.coverAmount.toString())
                loading?.setSelection(Position.loading(ConfigureBenefits.array[x].inputs.loading))
                remove?.visibility = View.VISIBLE
                break
            }
        }
    }
    private fun setAdapter(activity: Activity) {
        val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.loading)
        loading?.adapter = loadingAdapter
    }

}