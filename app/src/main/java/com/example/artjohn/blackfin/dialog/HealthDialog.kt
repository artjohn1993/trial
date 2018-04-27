package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.BenefitsActivity
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.BenefitsAdapter
import com.example.artjohn.blackfin.model.*
import org.greenrobot.eventbus.EventBus
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

        fun show(activity: Activity,product : Product.List?,provider : Provider.Result?) {
            var productPass = product
            var providerPass = provider

            println("======-----open dialog-----========")
            println(productPass)
            dialog = Dialog(activity)
            dialog?.setContentView(R.layout.dialog_health_layout)
            dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
            dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)


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
                var excessVal = 0
                if(!excess?.selectedItem.toString().substringBefore(" ").equals("Nil"))
                {
                    excessVal = excess?.selectedItem.toString().substringBefore(" ").toInt()
                }
                var loading = loading?.selectedItem.toString().substringBefore("%").toDouble()
                var calculated = LoadingPercentage(loading).calculate()
                var benefitsProduct = ProcessProduct().getListProduct(productPass ,providerPass,1)

                println(benefitsProduct.size)
                configuredBenefits(excessVal, STcheck, GPcheck,DOcheck,calculated,benefitsProduct)

                dialog?.hide()


            }

            dialog?.show()

        }
        fun configuredBenefits(excess : Int, ST : Boolean, GP : Boolean,DO : Boolean,loading : Double,benefitsProduct : List<BenefitsProductList>)
        {

            var dentalOptical : Boolean = DO
            var specialistsTest : Boolean = ST
            var benefitPeriod : Int = 0
            var calcPeriod : Int = 1
            var isAccelerated : Boolean = false
            var gpPrescriptions : Boolean = GP
            var frequency : Int = 12
            var isLifeBuyback : Boolean = false
            var isTpdAddon : Boolean = false
            var benefitPeriodType : String = "Term"
            var occupationType : String = "AnyOccupation"
            var wopWeekWaitPeriod : Int = 0
            var isFutureInsurability : Boolean = false
            var booster : Boolean = false
            var excess : Int = excess
            var coverAmount : Double = 0.0
            var loading : Double = loading
            var isTraumaBuyback : Boolean = false
            var benefitsProduct = benefitsProduct



            val data = Benefits(dentalOptical,
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
                    isTraumaBuyback,
                    benefitsProduct
            )

            var inputs = Inputs(1,data)


            if(ConfigureBenefits.id.contains(1))
            {
                var index = ConfigureBenefits.id.indexOf(1)
                ConfigureBenefits.array.set(index,inputs)
            }
            else
            {

                ConfigureBenefits.id.add(1)
                EventBus.getDefault().post(ConfigureBenefits(inputs))
            }


        }

    }
}