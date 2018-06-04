package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class RedundancyDialog {
    var dialog : Dialog? = null


    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        dialog = Dialog(activity)
        dialog?.setContentView(R.layout.layout_redundancy)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)


        dialog?.show()
    }
}