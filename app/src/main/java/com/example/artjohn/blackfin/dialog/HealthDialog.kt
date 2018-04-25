package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import com.example.artjohn.blackfin.R

class HealthDialog : AppCompatActivity(){

    companion object {
        var dialog : Dialog? = null
        var closeButton : ImageView? = null
        var excess : Spinner? = null
        var loading : Spinner? = null

        var excessArray = arrayOf(
                "Nil - Excess",
                "250 Excess",
                "500 Excess",
                "1000 Excess",
                "2000 Excess",
                "3000 Excess",
                "4000 Excess",
                "6000 Excess",
                "10000 Excess"
        )
        var loadingArray = arrayOf(
                "0%",
                "50%",
                "757%",
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

        fun show(activity: Activity){
            dialog = Dialog(activity)
            dialog?.setContentView(R.layout.dialog_health_layout)
            dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
            dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)

            closeButton = dialog?.findViewById<ImageView>(R.id.closeButton)
            excess = dialog?.findViewById<Spinner>(R.id.excessSpinner)
            loading = dialog?.findViewById<Spinner>(R.id.loadingSpinner)


            val statusAdapter : ArrayAdapter<String> = ArrayAdapter(activity, android.R.layout.simple_list_item_1,excessArray)
            excess?.adapter = statusAdapter

            val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity, android.R.layout.simple_list_item_1, loadingArray)
            loading?.adapter = loadingAdapter

            closeButton?.setOnClickListener {
                dialog?.hide()
            }

            dialog?.show()
        }


    }
}