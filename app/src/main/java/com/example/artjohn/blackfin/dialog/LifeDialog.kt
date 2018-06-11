package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.array.PublicArray
import com.example.artjohn.blackfin.event.*
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
    var remove : Button? = null
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
        setDialog(activity)
        dialogViewId(dialog)
        setAdapter(activity)
        setConfiguredBenefits(id)

        closeButton?.setOnClickListener {
            dialog?.hide()
        }
        indexed?.setOnCheckedChangeListener { buttonView, isChecked ->
            indexedcheck = isChecked
        }
        FI?.setOnCheckedChangeListener { buttonView, isChecked ->
            FIcheck = isChecked
        }
        remove?.setOnClickListener {
            dialog?.hide()
            EventBus.getDefault().post(RemoveConfiguredBenefits(clientID,2))
        }
        apply?.setOnClickListener {
            if (!amount?.text.isNullOrEmpty()) {
                dialog?.hide()

                cal = Conversion.calPeriod(calcuSpinner?.selectedItem.toString())
                var loadingVal: Double = Conversion.loading(this.loading?.selectedItem.toString())
                amountVal = Conversion.coverAmount(amount?.text.toString())
                var benefitsProduct = ProcessProduct().getListProduct(productPass,
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
                        loadingVal,
                        false,
                        benefitsProduct,
                        "Life Cover",
                        id,
                        2)
            }
        }
        dialog?.show()
        }
    //endregion

    //region - Private methods
    private fun setDialog(activity: Activity) {
        dialog = Dialog(activity)
        dialog?.setContentView(R.layout.layout_life)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
    }
    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById<ImageView>(R.id.closeButton)
        loading = dialog?.findViewById<Spinner>(R.id.loadingSpinner)
        calcuSpinner = dialog?.findViewById<Spinner>(R.id.calcuSpinner)
        apply = dialog?.findViewById<Button>(R.id.lifeApplyButton)
        remove = dialog?.findViewById<Button>(R.id.removeButton)
        amount = dialog?.findViewById<EditText>(R.id.coverAmountEdit)
        indexed = dialog?.findViewById<Switch>(R.id.indexedSwitch)
        FI = dialog?.findViewById<Switch>(R.id.FISwitch)
    }
    private fun setAdapter(activity: Activity) {
        loadingAdapter = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.loading)
        loading?.adapter = loadingAdapter
        calAdapter = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.calPeriod)
        calcuSpinner?.adapter = calAdapter
    }
    private fun setConfiguredBenefits(id : Int) {
        for (x in 0 until ConfigureBenefits.array.size) {
            if (ConfigureBenefits.array[x].clientId == id && ConfigureBenefits.array[x].inputs.benefitProductList[0].benefitId == 2) {
                amount?.setText(ConfigureBenefits.array[x].inputs.coverAmount.toString())
                loading?.setSelection(Position.loading(ConfigureBenefits.array[x].inputs.loading))
                FI?.isChecked = ConfigureBenefits.array[x].inputs.isFutureInsurability
                calcuSpinner?.setSelection(Position.calPeriod(ConfigureBenefits.array[x].inputs.calcPeriod))
                remove?.visibility = View.VISIBLE
                break
            }
        }
    }
    //endregion
}