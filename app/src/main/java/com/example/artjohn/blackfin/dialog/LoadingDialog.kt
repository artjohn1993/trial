package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.ViewGroup
import com.example.artjohn.blackfin.R

class LoadingDialog(var activity: Activity) {

    //region - Variables
    var dialog : Dialog? = null
    //endregion

    //region - Public methods
    fun show() {
        this.dialog = Dialog(activity)
        this.dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.dialog?.setContentView(R.layout.layout_loading)
        this.dialog?.setCancelable(false)
        this.dialog?.show()
    }
    fun hide() {
        dialog?.hide()
    }
    //endregion
}