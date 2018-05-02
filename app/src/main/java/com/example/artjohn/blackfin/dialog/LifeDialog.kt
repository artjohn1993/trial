package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.event.LoadingPercentage
import com.example.artjohn.blackfin.event.ProcessProduct
import com.example.artjohn.blackfin.model.*
import org.greenrobot.eventbus.EventBus

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
        fun show(activity: Activity, product : Product.List?, provider : Provider.Result?)  {
            var productPass = product
            var providerPass = provider
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
                var benefitsProduct = ProcessProduct().getListProduct(productPass ,providerPass,2)
                configuredBenefits(amountVal, cal, indexedcheck,FIcheck,loading,benefitsProduct)


            }

            dialog?.show()

        }
        fun configuredBenefits(amount : Double, cal : Int, indexed : Boolean,FI : Boolean,loading : Double, benefitsProduct : List<BenefitsProductList>)
        {
            val dentalOptical : Boolean = false
            val specialistsTest : Boolean = false
            val benefitPeriod : Int = 0
            val calcPeriod : Int = cal
            val isAccelerated : Boolean = false
            val gpPrescriptions : Boolean = false
            val frequency : Int = 12
            val isLifeBuyback : Boolean = false
            val isTpdAddon : Boolean = false
            val benefitPeriodType : String = "Term"
            val occupationType : String = "AnyOccupation"
            val wopWeekWaitPeriod : Int = 0
            val isFutureInsurability : Boolean = FI
            val booster : Boolean = false
            val excess : Int = 0
            val coverAmount : Double = amount
            val loading : Double = loading
            val isTraumaBuyback : Boolean = false
            val benefitsProduct = benefitsProduct

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