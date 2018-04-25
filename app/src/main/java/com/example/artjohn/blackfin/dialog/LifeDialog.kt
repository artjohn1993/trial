package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import org.jetbrains.anko.find

class LifeDialog: AppCompatActivity() {

    companion object {

        var dialog : Dialog? = null
        var closeButton : ImageView? = null
        var loading : Spinner? = null
        var calcuSpinner : Spinner? = null
        var apply : Button? = null
        var amount : EditText? = null
        var indexed : Switch? = null
        var FI : Switch? = null
        var indexedcheck : Boolean = false
        var FIcheck : Boolean = false
        var returnBol : Boolean = false



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
        fun show(activity: Activity) : Boolean {
            dialog = Dialog(activity)
            dialog?.setContentView(R.layout.dialog_life_layout)
            dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)

            closeButton = dialog?.findViewById<ImageView>(R.id.closeButton)
            loading = dialog?.findViewById<Spinner>(R.id.loadingSpinner)
            calcuSpinner = dialog?.findViewById<Spinner>(R.id.calcuSpinner)
            apply = dialog?.findViewById<Button>(R.id.lifeApplyButton)
            amount = dialog?.findViewById<EditText>(R.id.coverAmountEdit)
            indexed = dialog?.findViewById<Switch>(R.id.indexedSwitch)
            FI = dialog?.findViewById<Switch>(R.id.FISwitch)

            val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity, android.R.layout.simple_list_item_1, loadingArray)
            loading?.adapter = loadingAdapter

            val calAdapter : ArrayAdapter<String> = ArrayAdapter(activity, android.R.layout.simple_list_item_1, calArray)
            calcuSpinner?.adapter = calAdapter

            closeButton?.setOnClickListener {
                dialog?.hide()
                returnBol = false
            }

            indexed?.setOnCheckedChangeListener { buttonView, isChecked ->
                indexedcheck = isChecked
            }
            FI?.setOnCheckedChangeListener { buttonView, isChecked ->
                FIcheck = isChecked
            }

            apply?.setOnClickListener {
                println(amount?.text.toString())
                println(calcuSpinner?.selectedItem.toString())
                println(loading?.selectedItem.toString())
                println(indexedcheck)
                println(FIcheck)
                returnBol = true
            }

            dialog?.show()

            return returnBol
        }
    }
}