package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.array.PublicArray
import com.example.artjohn.blackfin.event.ConfiguredBenefits
import com.example.artjohn.blackfin.event.ConvertBenefitPeriod
import com.example.artjohn.blackfin.event.ProcessProduct
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class MortgageRepaymentDialog {
    var dialog : Dialog? = null
    var closeButton : ImageView? = null
    var coverAmount : EditText? = null
    var waitPeriod : Spinner? = null
    var benefitPeriod : Spinner? = null
    var loading : Spinner? = null
    var index : Switch? = null
    var applyButton : Button? = null

    var coverAmountVal : Double = 0.0
    var waitPeriodVal : Int = 0
    var benefitPeriodVal : Int = 0
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
        var benefitsProduct = ProcessProduct().getListProduct(product ,
                provider,
                7)
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
            waitPeriodVal = waitPeriod?.selectedItem.toString().substringAfter("week ").toInt()
            benefitPeriodVal = ConvertBenefitPeriod.value(benefitPeriod?.selectedItem.toString())
            loadingVal = loading?.selectedItem.toString().substringBefore("%").toDouble()
            ConfiguredBenefits(false,
                    false,
                    benefitPeriodVal,
                    1,
                    false,
                    false,
                    12,
                    false,
                    false,
                    "Term",
                    "AnyOccupation",
                    waitPeriodVal,
                    false,
                    false,
                    0,
                    coverAmountVal,
                    loadingVal,
                    false,
                    benefitsProduct,
                    "Mortgage Repayment Cover",
                    id,
                    7)
        }

        dialog?.show()
    }
    private fun setupDialog(dialog: Dialog?) {
        dialog?.setContentView(R.layout.layout_mortgage_repayment)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
    }
    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById(R.id.closeButton)
        coverAmount = dialog?.findViewById(R.id.coverAmountEdit)
        waitPeriod = dialog?.findViewById(R.id.waitPeriodSpinner)
        benefitPeriod = dialog?.findViewById(R.id.benefitPeriodSpinner)
        loading = dialog?.findViewById(R.id.loadingSpinner)
        index = dialog?.findViewById(R.id.indexedSwitch)
        applyButton = dialog?.findViewById(R.id.applyButton)
    }
    private fun setAdapter(activity: Activity) {
        val waitPeriodAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.waitPeriod)
        val benefitPeriodAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.benefitPeriod)
        val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.loading)
        waitPeriod?.adapter = waitPeriodAdapter
        benefitPeriod?.adapter = benefitPeriodAdapter
        loading?.adapter = loadingAdapter

    }
}