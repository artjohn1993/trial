package com.example.artjohn.blackfin.presenter

import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.QouteRequest
import java.math.RoundingMode
import java.text.DecimalFormat

class BreakDownPresenterClass(val view : BreakDownView) : BreakDownPresenter {

    //region - Variables
    var imageArray : Array<Int> = arrayOf(
            R.drawable.ic_accuro,
            R.drawable.ic_aia,
            R.drawable.ic_amp,
            R.drawable.ic_amprpp,
            R.drawable.ic_asteron,
            R.drawable.ic_fidelity,
            R.drawable.ic_nib,
            R.drawable.ic_onepath,
            R.drawable.ic_partnerslife,
            R.drawable.ic_southerncross,
            R.drawable.ic_sovereign,
            R.drawable.ic_fidelity
    )
    var name : String = ""
    var age : Int = 0
    var status : Array<String>? = null
    var smoker = ""
    var  gender = ""
    var clientClass : String = ""
    var currentStatus : String = ""
    //endregion

    //region - BreakDownPresenter Delegate
    override fun processLogo(id : Int) {
        view.setLogo(imageArray[id-1])
    }

    override fun processPremiumPrice(price: Double) {
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        view.setPremiumPrice(df.format(price))
    }

    override fun processPT(data: QouteRequest.Result,index : Int) {
        view.setPT(data.data.data.result.providers[index].policyFee)
    }
    //endregion
}

interface BreakDownView {

    fun setLogo(image :  Int)
    fun setPremiumPrice(price : String)
    fun setPT(fee : Double)
}

interface BreakDownPresenter {

    fun processLogo(id : Int)
    fun processPremiumPrice(price : Double)
    fun processPT(
            data : QouteRequest.Result,
            index : Int)
}