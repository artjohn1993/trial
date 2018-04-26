package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.BenefitsActivity
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.BenefitsAdapter
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.LoadingPercentage
import com.example.artjohn.blackfin.model.Qoute
import org.greenrobot.eventbus.EventBus
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
        fun show(activity: Activity)  {
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

            }

            indexed?.setOnCheckedChangeListener { buttonView, isChecked ->
                indexedcheck = isChecked
            }
            FI?.setOnCheckedChangeListener { buttonView, isChecked ->
                FIcheck = isChecked
            }

            apply?.setOnClickListener {
               /* println(amount?.text.toString())
                println(calcuSpinner?.selectedItem.toString())
                println(loading?.selectedItem.toString())
                println(indexedcheck)
                println(FIcheck)*/
                dialog?.hide()

                var cal : Int = 0
                if(calcuSpinner?.selectedItemPosition != null)
                {
                    cal = calcuSpinner!!.selectedItemPosition
                }
                var loading = loading?.selectedItem.toString().substringBefore("%").toDouble()
                var amountVal : Double = 0.0
                try {
                    amountVal = amount?.text.toString().toDouble()
                }catch (e : Exception)
                {
                    amountVal = 0.0
                }
                var calculated = LoadingPercentage(loading).calculate()

                configuredBenefits(amountVal, cal, indexedcheck,FIcheck,loading)


            }

            dialog?.show()

        }
        fun configuredBenefits(amount : Double, cal : Int, indexed : Boolean,FI : Boolean,loading : Double)
        {
            var dentalOptical : Boolean = false
            var specialistsTest : Boolean = false
            var benefitPeriod : Int = 0
            var calcPeriod : Int = cal
            var isAccelerated : Boolean = false
            var gpPrescriptions : Boolean = false
            var frequency : Int = 12
            var isLifeBuyback : Boolean = false
            var isTpdAddon : Boolean = false
            var benefitPeriodType : String = "Term"
            var occupationType : String = "AnyOccupation"
            var wopWeekWaitPeriod : Int = 0
            var isFutureInsurability : Boolean = FI
            var booster : Boolean = false
            var excess : Int = 0
            var coverAmount : Double = amount
            var loading : Double = loading
            var isTraumaBuyback : Boolean = false

            val data = Qoute.Benefits(dentalOptical,
                    specialistsTest,
                    benefitPeriod,
                    calcPeriod,
                    isAccelerated,
                    gpPrescriptions,
                    frequency,
                    isLifeBuyback,
                    isTpdAddon,
                    benefitPeriodType,
                    occupationType,
                    wopWeekWaitPeriod,
                    isFutureInsurability,
                    booster,
                    excess,
                    coverAmount,
                    loading,
                    isTraumaBuyback
            )
            var inputs = Qoute.Inputs(1,data)


            if(ConfigureBenefits.id.contains(2))
            {
                var index = ConfigureBenefits.id.indexOf(2)
                ConfigureBenefits.array.set(index,inputs)
            }
            else
            {
                EventBus.getDefault().post(ConfigureBenefits(inputs))
                ConfigureBenefits.id.add(2)

            }



        }
    }
}