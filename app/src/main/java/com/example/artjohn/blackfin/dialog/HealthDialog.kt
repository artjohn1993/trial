package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.BenefitsActivity
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.BenefitsAdapter
import org.jetbrains.anko.find

class HealthDialog : AppCompatActivity(){

    companion object {
        var dialog : Dialog? = null
        var closeButton : ImageView? = null
        var excess : Spinner? = null
        var loading : Spinner? = null
        var apply : Button? = null
        var ST : Switch? = null
        var GP : Switch? = null
        var DO : Switch? = null
        var STcheck : Boolean = false
        var GPcheck : Boolean = false
        var DOcheck : Boolean = false
        var returnBol : Boolean = false


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

        fun show(activity: Activity) : Boolean{
            dialog = Dialog(activity)
            dialog?.setContentView(R.layout.dialog_health_layout)
            dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
            dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
            dialog?.show()

            closeButton = dialog?.findViewById<ImageView>(R.id.closeButton)
            excess = dialog?.findViewById<Spinner>(R.id.excessSpinner)
            loading = dialog?.findViewById<Spinner>(R.id.loadingSpinner)
            apply = dialog?.findViewById<Button>(R.id.healthApplyButton)
            ST = dialog?.findViewById<Switch>(R.id.specialistSwitch)
            GP = dialog?.findViewById<Switch>(R.id.prescriptionSwitch)
            DO = dialog?.findViewById<Switch>(R.id.DOSwitch)


            val statusAdapter : ArrayAdapter<String> = ArrayAdapter(activity, android.R.layout.simple_list_item_1,excessArray)
            excess?.adapter = statusAdapter

            val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity, android.R.layout.simple_list_item_1, loadingArray)
            loading?.adapter = loadingAdapter

            closeButton?.setOnClickListener {
                dialog?.hide()
                returnBol = false
                var holder = BenefitsAdapter(activity)
                holder.selected.remove(0)
                holder.notifyDataSetChanged()
            }
            ST?.setOnCheckedChangeListener { buttonView, isChecked ->
                STcheck = isChecked
            }
            GP?.setOnCheckedChangeListener { buttonView, isChecked ->
                GPcheck = isChecked
            }
            DO?.setOnCheckedChangeListener { buttonView, isChecked ->
                DOcheck = isChecked
            }

            apply?.setOnClickListener {

              /*  println(excess?.selectedItem.toString())
                println(STcheck)
                println(GPcheck)
                println(DOcheck)
                println(loading?.selectedItem.toString())*/
                returnBol = true
                dialog?.hide()


            }


            return returnBol
        }


    }
}