package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class IncomeProtectionDialog {
    var dialog : Dialog? = null


    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        dialog = Dialog(activity)
        dialog?.setContentView(R.layout.layout_income_protection)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)


        dialog?.show()
    }
}