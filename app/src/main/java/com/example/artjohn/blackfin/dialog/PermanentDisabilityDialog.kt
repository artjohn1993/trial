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
import com.example.artjohn.blackfin.model.QouteSettings
import org.greenrobot.eventbus.EventBus

class PermanentDisabilityDialog {
    var dialog : Dialog? = null
    var closeButton : ImageView? = null
    var coverAmount : EditText? = null
    var occupation : Spinner? = null
    var calPeriod : Spinner? = null
    var loading : Spinner? = null
    var index : Switch? = null
    var applyButton : Button? = null
    var remove : Button? = null
    var coverAmountVal : Double = 0.0
    var occupationVal : String = ""
    var calPeriodVal : Int = 0
    var loadingVal : Double = 0.0
    var indexVal : Boolean = false

    fun show(activity: Activity,
             data : QouteSettings.Result,
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
            EventBus.getDefault().post(RemoveConfiguredBenefits(id,7))
        }
        applyButton?.setOnClickListener {
            if (!coverAmount?.text.isNullOrEmpty()) {
                dialog?.hide()
                coverAmountVal = Conversion.coverAmount(coverAmount?.text.toString())
                occupationVal = Conversion.occupationType(occupation?.selectedItem.toString())
                calPeriodVal = Conversion.calPeriod(calPeriod?.selectedItem.toString())
                loadingVal = Conversion.loading(loading?.selectedItem.toString())
                var benefitsProduct = ProcessProduct().getListProduct(data,
                        5)
                ConfiguredBenefits(false,
                        false,
                        0,
                        calPeriodVal,
                        false,
                        false,
                        12,
                        false,
                        false,
                        "Term",
                        occupationVal,
                        0,
                        false,
                        false,
                        0,
                        coverAmountVal,
                        loadingVal,
                        false,
                        benefitsProduct,
                        "Total & Permanent Disability Cover",
                        id,
                        5)
            }
        }


        dialog?.show()
    }

    private fun setupDialog(customDialog: Dialog?) {
        dialog?.setContentView(R.layout.layout_permanent_disability)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
    }
    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById(R.id.closeButton)
        coverAmount = dialog?.findViewById(R.id.coverAmountEdit)
        occupation = dialog?.findViewById(R.id.occupationSpinner)
        calPeriod = dialog?.findViewById(R.id.calPeriodSpinner)
        loading = dialog?.findViewById(R.id.loadingSpinner)
        index = dialog?.findViewById(R.id.indexedSwitch)
        applyButton = dialog?.findViewById(R.id.applyButton)
        remove = dialog?.findViewById(R.id.removeButton)
    }
    private fun setConfiguredBenefits(id : Int) {
        for (x in 0 until ConfigureBenefits.array.size) {
            if (ConfigureBenefits.array[x].clientId == id && ConfigureBenefits.array[x].inputs.benefitProductList[0].benefitId == 5) {
                coverAmount?.setText(ConfigureBenefits.array[x].inputs.coverAmount.toString())
                calPeriod?.setSelection(Position.calPeriod(ConfigureBenefits.array[x].inputs.calcPeriod))
                occupation?.setSelection(Position.occupationType(ConfigureBenefits.array[x].inputs.occupationType))
                calPeriod?.setSelection(Position.calPeriod(ConfigureBenefits.array[x].inputs.calcPeriod))
                loading?.setSelection(Position.loading(ConfigureBenefits.array[x].inputs.loading))
                remove?.visibility = View.VISIBLE
                break
            }
        }
    }
    private fun setAdapter(activity: Activity) {
        val occupationAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.occupationType)
        val calperiodAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.calPeriod)
        val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.loading)
        occupation?.adapter = occupationAdapter
        calPeriod?.adapter = calperiodAdapter
        loading?.adapter = loadingAdapter
    }
}