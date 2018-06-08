package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.array.PublicArray
import com.example.artjohn.blackfin.event.ConfiguredBenefits
import com.example.artjohn.blackfin.event.Conversion
import com.example.artjohn.blackfin.event.Position
import com.example.artjohn.blackfin.event.ProcessProduct
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class WaiverPremiumDialog {
    var dialog : Dialog? = null
    var closeButton : ImageView? = null
    var applyButton : Button? = null
    var waitPeriod : Spinner? = null
    var loading : Spinner? = null

    var waitPeriodVal : Int = 0
    var loadingVal : Double = 0.0

    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        dialog = Dialog(activity)
        setupDialog(dialog)
        dialogViewId(dialog)
        setAdapter(activity)
        setConfiguredBenefits(id)
        closeButton?.setOnClickListener {
            dialog?.hide()
        }
        applyButton?.setOnClickListener {
            dialog?.hide()
            waitPeriodVal = Conversion.waitPeriod(waitPeriod?.selectedItem.toString())
            loadingVal = Conversion.loading(loading?.selectedItem.toString())
            var benefitsProduct = ProcessProduct().getListProduct(product ,
                    provider,
                    9)
            ConfiguredBenefits(false,
                    false,
                    0,
                    1,
                    false,
                    false,
                    12,
                    false,
                    false,
                    "Term",
                    "AnyOccupation",
                    waitPeriodVal,
                    false,
                    false,
                    0,
                    0.0,
                    loadingVal,
                    false,
                    benefitsProduct,
                    "Waiver of Premium",
                    id,
                    9)
        }


        dialog?.show()
    }
    private fun setupDialog(dialog : Dialog?) {
        dialog?.setContentView(R.layout.layout_waiver_of_premium)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
    }
    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById(R.id.closeButton)
        loading =  dialog?.findViewById(R.id.loadingSpinner)
        waitPeriod = dialog?.findViewById(R.id.waitPeriodSpinner)
        applyButton = dialog?.findViewById(R.id.applyButton)
    }
    private fun setConfiguredBenefits(id : Int) {
        for (x in 0 until ConfigureBenefits.array.size) {
            if (ConfigureBenefits.array[x].clientId == id && ConfigureBenefits.array[x].inputs.benefitProductList[0].benefitId == 9) {
                loading?.setSelection(Position.loading(ConfigureBenefits.array[x].inputs.loading))
                waitPeriod?.setSelection(Position.waitPeriod(ConfigureBenefits.array[x].inputs.wopWeekWaitPeriod))
                break
            }
        }
    }
    private fun setAdapter(activity: Activity) {
        val waitPeriodAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.waitPeriod)
        val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.loading)
        waitPeriod?.adapter = waitPeriodAdapter
        loading?.adapter = loadingAdapter

    }
}