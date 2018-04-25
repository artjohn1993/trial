package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import com.example.artjohn.blackfin.R

class LifeDialog: AppCompatActivity() {

    companion object {

        var dialog : Dialog? = null
        var closeButton : ImageView? = null
        var loading : Spinner? = null
        var calcuSpinner : Spinner? = null
        var loadingArray = arrayOf(
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
        var calArray = arrayOf(
                "Yearly Renewable",
                "Level (10 Years)",
                "Level (15 Years)",
                "Level (To Age 65)",
                "Level (To Age 70)",
                "Level (To Age 80)",
                "Level (To Age 85)",
                "Level (To Age 90)",
                "Level (To Age 100)"
        )
        fun show(activity: Activity){
            dialog = Dialog(activity)
            dialog?.setContentView(R.layout.dialog_life_layout)
            dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)

            closeButton = dialog?.findViewById<ImageView>(R.id.closeButton)
            loading = dialog?.findViewById<Spinner>(R.id.loadingSpinner)
            calcuSpinner = dialog?.findViewById<Spinner>(R.id.calcuSpinner)

            val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity, android.R.layout.simple_list_item_1, loadingArray)
            loading?.adapter = loadingAdapter

            val calAdapter : ArrayAdapter<String> = ArrayAdapter(activity, android.R.layout.simple_list_item_1, calArray)
            calcuSpinner?.adapter = calAdapter

            closeButton?.setOnClickListener {
                dialog?.hide()
            }

            dialog?.show()
        }
    }
}