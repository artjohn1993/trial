package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class FamilyProtectionDialog {

    var dialog : Dialog? = null
    var closeButton : ImageView? = null
    var cover : EditText? = null
    var termPeriod : Spinner? = null
    var yearPeriod : Spinner? = null
    var loading : Spinner? = null
    var index : Switch? = null
    var apply : Button? = null
    var loadingArray = arrayOf (
            "0%",
            "50%",
            "75%",
            "100%",
            "125%",
            "150%",
            "175%",
            "200%",
            "250%",
            "300%",
            "400%",
            "500%"
    )
    var benefitPeriodArray = arrayOf(
            "Fixed Term",
            "To Age"
    )
    var yearsArray : ArrayList<String> = ArrayList()


    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        dialog = Dialog(activity)
        setupDialog(dialog)
        dialogViewId(dialog)
        setyears()
        setAdapters(activity)

        closeButton?.setOnClickListener {
            dialog?.hide()
        }

        dialog?.show()
    }

    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById(R.id.closeButton)
        cover = dialog?.findViewById(R.id.coverAmountEdit)
        termPeriod = dialog?.findViewById(R.id.termPeriodSpinner)
        yearPeriod = dialog?.findViewById(R.id.yearsPeriodSpinner)
        loading = dialog?.findViewById(R.id.loadingSpinner)
        index = dialog?.findViewById(R.id.indexedSwitch)
        apply = dialog?.findViewById(R.id.applyButton)
    }
    private fun setupDialog(customDialog: Dialog?) {
        customDialog?.setContentView(R.layout.layout_family_protection)
        customDialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        customDialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
    }
    private fun setyears() {
        for (year in 1..30) {
            yearsArray.add("$year Years")
        }
    }
    private fun setAdapters(activity: Activity) {
        val benefitAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                benefitPeriodArray)
        val yearsAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                yearsArray)
        val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                loadingArray)
        termPeriod?.adapter = benefitAdapter
        yearPeriod?.adapter = yearsAdapter
        loading?.adapter = loadingAdapter

    }
}