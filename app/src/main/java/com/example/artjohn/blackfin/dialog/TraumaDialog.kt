package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.array.PublicArray
import com.example.artjohn.blackfin.event.ConfiguredBenefits
import com.example.artjohn.blackfin.event.Conversion
import com.example.artjohn.blackfin.event.Position
import com.example.artjohn.blackfin.event.ProcessProduct
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class TraumaDialog {
    var dialog : Dialog? = null
    var closeButton : ImageView? = null
    var cover : EditText? = null
    var type : Spinner? = null
    var calPeriod : Spinner? = null
    var indexed : Switch? = null
    var trauma : Switch? = null
    var tpd : Switch? = null
    var applyButton : Button? = null

    var coverVal : Double = 0.0
    var occupationType : String = ""
    var calPeriodVal : Int = 0
    var indexVal : Boolean = false
    var traumaBBVal : Boolean = false
    var tpdVal : Boolean = false

    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        dialog = Dialog(activity)
        setupDialog(dialog)
        dialogViewId(dialog)
        setAdapter(activity)
        setConfiguredBenefits(id)
        indexed?.setOnCheckedChangeListener { buttonView, isChecked ->
            indexVal = isChecked
        }
        trauma?.setOnCheckedChangeListener { buttonView, isChecked ->
            traumaBBVal = isChecked
        }
        tpd?.setOnCheckedChangeListener { buttonView, isChecked ->
            tpdVal = isChecked
        }
        closeButton?.setOnClickListener {
            dialog?.hide()
        }
        applyButton?.setOnClickListener {
            dialog?.hide()
            coverVal = Conversion.coverAmount(cover?.text.toString())
            occupationType = Conversion.occupationType(type?.selectedItem.toString())
            calPeriodVal = Conversion.calPeriod(calPeriod?.selectedItem.toString())
            var benefitsProduct = ProcessProduct().getListProduct(product ,
                    provider,
                    4)

            ConfiguredBenefits(false,
                    false,
                    0,
                    calPeriodVal,
                    false,
                    false,
                    12,
                    false,
                    tpdVal,
                    "Term",
                    occupationType,
                    0,
                    false,
                    false,
                    0,
                    coverVal,
                    0.0,
                    traumaBBVal,
                    benefitsProduct,
                    "Trauma Cover",
                    id,
                    4)
        }

        dialog?.show()
    }

    private fun setupDialog(customDialog: Dialog?) {
        dialog?.setContentView(R.layout.layout_trauma)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
    }
    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById(R.id.closeButton)
        cover = dialog?.findViewById(R.id.coverAmountEdit)
        type = dialog?.findViewById(R.id.typeSpinner)
        calPeriod = dialog?.findViewById(R.id.calPeriodSpinner)
        indexed = dialog?.findViewById(R.id.indexedSwitch)
        trauma = dialog?.findViewById(R.id.traumaBuyBackSwitch)
        tpd = dialog?.findViewById(R.id.addOnSwitch)
        applyButton = dialog?.findViewById(R.id.applyButton)
    }
    private fun setConfiguredBenefits(id : Int) {
        for (x in 0 until ConfigureBenefits.array.size) {
            if (ConfigureBenefits.array[x].clientId == id && ConfigureBenefits.array[x].inputs.benefitProductList[0].benefitId == 4) {
                cover?.setText(ConfigureBenefits.array[x].inputs.coverAmount.toString())
                calPeriod?.setSelection(Position.calPeriod(ConfigureBenefits.array[x].inputs.calcPeriod))
                type?.setSelection(Position.occupationType(ConfigureBenefits.array[x].inputs.occupationType))
                traumaBBVal = ConfigureBenefits.array[x].inputs.isTraumaBuyback
                tpdVal = ConfigureBenefits.array[x].inputs.isTpdAddon
                trauma?.isChecked = traumaBBVal
                tpd?.isChecked = tpdVal
                break
            }
        }
    }
    private fun setAdapter(activity: Activity) {
        val typeAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.occupationType)
        val calPerioddapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.calPeriod)
        type?.adapter = typeAdapter
        calPeriod?.adapter = calPerioddapter
    }
}