package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.array.PublicArray
import com.example.artjohn.blackfin.event.ConfiguredBenefits
import com.example.artjohn.blackfin.event.ProcessProduct
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class RedundancyDialog {
    var dialog : Dialog? = null
    var closeButton : ImageView? = null
    var coverAmount : EditText? = null
    var loading : Spinner? = null
    var index : Switch? = null
    var applyButton : Button? = null

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

        closeButton?.setOnClickListener {
            dialog?.hide()
        }
        applyButton?.setOnClickListener {
            dialog?.hide()
            var benefitsProduct = ProcessProduct().getListProduct(product ,
                    provider,
                    8)
            try {
                coverAmountVal = coverAmount?.text.toString().toDouble()
            } catch (e : Exception) {
                coverAmountVal = 0.0
            }
            loadingVal = loading?.selectedItem.toString().substringBefore("%").toDouble()

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

    }
    private fun setAdapter(activity: Activity) {
        val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.loading)
        loading?.adapter = loadingAdapter
    }

}