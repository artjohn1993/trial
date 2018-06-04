package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class TraumaDialog {
    var dialog : Dialog? = null
    var closeButton : ImageButton? = null
    var cover : EditText? = null
    var type : Spinner? = null
    var calPeriod : Spinner? = null
    var laoding : Spinner? = null

    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        dialog = Dialog(activity)
        setupDialog(dialog)


        dialog?.show()
    }

    private fun setupDialog(customDialog: Dialog?) {
        dialog?.setContentView(R.layout.layout_trauma)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
    }
}