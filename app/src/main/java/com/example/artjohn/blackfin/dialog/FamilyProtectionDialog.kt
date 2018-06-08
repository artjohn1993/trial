package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider
import android.widget.AdapterView
import com.example.artjohn.blackfin.array.PublicArray
import com.example.artjohn.blackfin.event.*
import com.example.artjohn.blackfin.model.ConfigureBenefits


class FamilyProtectionDialog {

    var dialog : Dialog? = null
    var closeButton : ImageView? = null
    var cover : EditText? = null
    var termPeriod : Spinner? = null
    var yearPeriod : Spinner? = null
    var loading : Spinner? = null
    var index : Switch? = null
    var apply : Button? = null


    var yearsArray1 : ArrayList<String> = ArrayList()
    var yearsArray2 : ArrayList<String> = ArrayList()
    var coverAmountVal : Double = 0.0
    var benefitTermPeriodVal : String = ""
    var benefitYearVal : Int = 0
    var loadingVal : Double = 0.0
    var indexVal : Boolean = false


    fun show(activity: Activity,
             product : Product.List?,
             provider : Provider.Result?,
             id : Int) {
        dialog = Dialog(activity as Context?)
        setupDialog(dialog)
        dialogViewId(dialog)
        setyears()
        setAdapters(activity)
        setConfiguredBenefits(id)
        closeButton?.setOnClickListener {
            dialog?.hide()
        }

        index?.setOnCheckedChangeListener { buttonView, isChecked ->
            indexVal = isChecked
        }

        termPeriod?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               when(position) {
                   0 -> {
                       setyearsAdapter1(activity)
                   }
                   1 -> {
                       setyearsAdapter2(activity)
                   }
               }
            }
        }

        apply?.setOnClickListener {
            dialog?.hide()
            loadingVal = Conversion.loading(loading?.selectedItem.toString())
            coverAmountVal = Conversion.coverAmount(cover?.text.toString())
            benefitYearVal = Conversion.yearPeriod(yearPeriod?.selectedItem.toString())
            benefitTermPeriodVal = Conversion.termPeriod(termPeriod?.selectedItem.toString())
            var benefitsProduct = ProcessProduct().getListProduct(product ,
                    provider,
                    3)

            ConfiguredBenefits(false,
                    false,
                    benefitYearVal,
                    1,
                    false,
                    false,
                    12,
                    false,
                    false,
                    benefitTermPeriodVal,
                    "AnyOccupation",
                    0,
                    false,
                    false,
                    0,
                    coverAmountVal,
                    loadingVal,
                    false,
                    benefitsProduct,
                    "Family Protection Cover",
                    id,
                    3)
        }
        dialog?.show()
    }

    private fun dialogViewId(dialog: Dialog?) {
        closeButton = dialog?.findViewById(R.id.closeButton)
        cover = dialog?.findViewById(R.id.coverAmountEdit)
        termPeriod = dialog?.findViewById(R.id.termPeriodSpinner)
        yearPeriod = dialog?.findViewById(R.id.yearsPeriodSpinner)
        loading = dialog?.findViewById(R.id.loadingSpinner)
        index = dialog?.findViewById(R.id.indexedSwitch)
        apply = dialog?.findViewById(R.id.lifeApplyButton)
    }
    private fun setConfiguredBenefits(id : Int) {
        for (x in 0 until ConfigureBenefits.array.size) {
            if (ConfigureBenefits.array[x].clientId == id && ConfigureBenefits.array[x].inputs.benefitProductList[0].benefitId == 3) {
                cover?.setText(ConfigureBenefits.array[x].inputs.coverAmount.toString())
                loading?.setSelection(Position.loading(ConfigureBenefits.array[x].inputs.loading))
                termPeriod?.setSelection(Position.termbenefit(ConfigureBenefits.array[x].inputs.benefitPeriodType))
                yearPeriod?.setSelection(Position.yearPeriod(ConfigureBenefits.array[x].inputs.benefitPeriod))
                break
            }
        }
    }
    private fun setupDialog(customDialog: Dialog?) {
        customDialog?.setContentView(R.layout.layout_family_protection)
        customDialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        customDialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
    }
    private fun setyears() {
        for (year in 1..30) {
            yearsArray1.add("$year Years")
        }
        for (year in 31..70) {
            yearsArray2.add("$year Years")
        }
    }
    private fun setAdapters(activity: Activity) {
        val benefitAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.benefitTerm)

        val loadingAdapter : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                PublicArray.loading)
        termPeriod?.adapter = benefitAdapter
        setyearsAdapter1(activity)
        loading?.adapter = loadingAdapter

    }
    private fun setyearsAdapter1(activity: Activity) {
        val yearsAdapter1 : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                yearsArray1)
        yearPeriod?.adapter = yearsAdapter1
    }
    private fun setyearsAdapter2(activity: Activity) {
        val yearsAdapter2 : ArrayAdapter<String> = ArrayAdapter(activity,
                android.R.layout.simple_list_item_1,
                yearsArray2)
        yearPeriod?.adapter = yearsAdapter2
    }

}