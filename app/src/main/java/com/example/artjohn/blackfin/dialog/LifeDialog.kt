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

class LifeDialog: AppCompatActivity() {

    //region - Variables
    var clientID : Int = 1
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
    var productPass : Product.List? = null
    var providerPass : Provider.Result? = null
    var loadingAdapter : ArrayAdapter<String>? = null
    var calAdapter : ArrayAdapter<String>? = null
    var cal : Int = 0
    var amountVal : Double = 0.0
    //endregion

    //region - Public methods
    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        this.clientID = id
        this.productPass = product
        this.providerPass = provider
        this.dialog = Dialog(activity)
        this.dialog?.setContentView(R.layout.dialog_life_layout)
        this.dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        this.dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
        dialogViewId(dialog)
        this.loadingAdapter = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.loading)
        this.loading?.adapter = loadingAdapter
        this.calAdapter = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.calPeriod)
        this.calcuSpinner?.adapter = calAdapter

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
            dialog?.hide()
            if(calcuSpinner?.selectedItemPosition != null) {
                cal = calcuSpinner!!.selectedItemPosition
            }

            var loading : Double = this.loading?.selectedItem.toString().substringBefore("%").toDouble()

            try {
                amountVal = amount?.text.toString().toDouble()
            } catch (e : Exception) {
                amountVal = 0.0
            }
            var calculated = LoadingPercentage(loading).calculate()
            var benefitsProduct = ProcessProduct().getListProduct(productPass ,
                    providerPass,
                    2)
            ConfiguredBenefits(false,
                    false,
                    0,
                    cal,
                    false,
                    false,
                    12,
                    false,
                    false,
                    "Term",
                    "AnyOccupation",
                    0,
                    FIcheck,
                    false,
                    0,
                    amountVal,
                    calculated,
                    false,
                    benefitsProduct,
            "Life Cover",
                    id,
            2)
        }
        dialog?.show()
        }
    //endregion

    //region - Private methods
    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById<ImageView>(R.id.closeButton)
        loading = dialog?.findViewById<Spinner>(R.id.loadingSpinner)
        calcuSpinner = dialog?.findViewById<Spinner>(R.id.calcuSpinner)
        apply = dialog?.findViewById<Button>(R.id.lifeApplyButton)
        amount = dialog?.findViewById<EditText>(R.id.coverAmountEdit)
        indexed = dialog?.findViewById<Switch>(R.id.indexedSwitch)
        FI = dialog?.findViewById<Switch>(R.id.FISwitch)
    }
    //endregion
}