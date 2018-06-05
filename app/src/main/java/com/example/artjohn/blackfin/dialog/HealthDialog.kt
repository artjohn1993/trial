package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.array.PublicArray
import com.example.artjohn.blackfin.event.ConfiguredBenefits
import com.example.artjohn.blackfin.event.LoadingPercentage
import com.example.artjohn.blackfin.event.ProcessProduct
import com.example.artjohn.blackfin.model.*
import org.greenrobot.eventbus.EventBus

class HealthDialog : AppCompatActivity() {
    //region - Variables
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
    var productPass : Product.List? = null
    var providerPass : Provider.Result? = null
    var clientID : Int = 1

        //endregion

    //region - Public methods
    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        clientID = id
        productPass = product
        providerPass = provider
        dialog = Dialog(activity)
        dialog?.setContentView(R.layout.dialog_health_layout)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
        dialogViewId(dialog)

        val statusAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.excess)
        excess?.adapter = statusAdapter

        val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.loading)
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
            var excessVal = 0
            if(!excess?.selectedItem.toString().substringBefore(" ").equals("Nil")) {
                excessVal = excess?.selectedItem.toString().substringBefore(" ").toInt()
            }
            var loading = loading?.selectedItem.toString().substringBefore("%").toDouble()
            var calculated = LoadingPercentage(loading).calculate()
            var benefitsProduct = ProcessProduct().getListProduct(productPass ,
                    providerPass,
                    1)

            ConfiguredBenefits(DOcheck,
                    STcheck,
                    0,
                    1,
                    false,
                    GPcheck,
                    12,
                    false,
                    false,
                    "Term",
                    "AnyOccupation",
                    0,
                    false,
                    false,
                    excessVal,
                    0.0,
                    calculated,
                    false,
                    benefitsProduct,
                    "Health Cover",
                    id,
                    1)
            dialog?.hide()
        }
        dialog?.show()
        }
        //endregion

    //region - Private methods
    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById<ImageView>(R.id.closeButton)
        excess = dialog?.findViewById<Spinner>(R.id.excessSpinner)
        loading = dialog?.findViewById<Spinner>(R.id.loadingSpinner)
        apply = dialog?.findViewById<Button>(R.id.healthApplyButton)
        ST = dialog?.findViewById<Switch>(R.id.specialistSwitch)
        GP = dialog?.findViewById<Switch>(R.id.prescriptionSwitch)
        DO = dialog?.findViewById<Switch>(R.id.DOSwitch)
    }
    //endregion
}