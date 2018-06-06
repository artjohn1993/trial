package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.array.PublicArray
import com.example.artjohn.blackfin.event.CalculatedPeriod
import com.example.artjohn.blackfin.event.ConfiguredBenefits
import com.example.artjohn.blackfin.event.ProcessProduct
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class PermanentDisabilityDialog {
    var dialog : Dialog? = null
    var closeButton : ImageView? = null
    var coverAmount : EditText? = null
    var occupation : Spinner? = null
    var typeSpinner : Spinner? = null
    var calPeriod : Spinner? = null
    var loading : Spinner? = null
    var index : Switch? = null
    var applyButton : Button? = null

    var coverAmountVal : Double = 0.0
    var occupationVal : String = ""
    var typeSpinnerVal : String = ""
    var calPeriodVal : Int = 0
    var loadingVal : Double = 0.0
    var indexVal : Boolean = false

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
            try {
                coverAmountVal = coverAmount?.text.toString().toDouble()
            } catch (e : Exception) {
                coverAmountVal = 0.0
            }
            occupationVal = occupation?.selectedItem.toString().toLowerCase()
            calPeriodVal = CalculatedPeriod.convert(calPeriod?.selectedItem.toString())
            loadingVal = loading?.selectedItem.toString().substringBefore("%").toDouble()
            var benefitsProduct = ProcessProduct().getListProduct(product ,
                    provider,
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
        typeSpinner = dialog?.findViewById(R.id.typeSpinner)
        calPeriod = dialog?.findViewById(R.id.calPeriodSpinner)
        loading = dialog?.findViewById(R.id.loadingSpinner)
        index = dialog?.findViewById(R.id.indexedSwitch)
        applyButton = dialog?.findViewById(R.id.applyButton)
    }
    private fun setAdapter(activity: Activity) {
        val occupationAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.occupationType)
        val typeAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.calPeriod)
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