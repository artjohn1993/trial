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

    //region - BreakDownPresenter methods
    override fun processProfile() {
        var gender = ClientInfo.array[0].gender
        var age = ClientInfo.array[0].age.toInt()

        if(gender.equals("M")) {
            if (age>18) {
                view.setProfile(R.drawable.icon_male)
            }
            else {
                view.setProfile(R.drawable.icon_boy)
            }
        }
        else {
            if (age>18) {
                view.setProfile(R.drawable.icon_female)
            }
            else {
                view.setProfile(R.drawable.icon_girl)
            }
        }
    }

    override fun processLogo(id : Int) {
        view.setLogo(imageArray[id-1])
    }

    override fun processDetails() {
        this.name = ClientInfo.array[0].name
        this.age = ClientInfo.array[0].age.toInt()
        this.status = arrayOf("Class 1","Class 2","Class 3","Class 4","Class 5")
        this.clientClass = this.status!![ClientInfo.array[0].occupationId - 1]
        this.currentStatus = ClientInfo.array[0].employedStatus

        if(ClientInfo.array[0].isChild){
            this.smoker = "Smoker"
        }
        else {
            this.smoker = "Not Smoker"
        }
        if(ClientInfo.array[0].gender == "M"){
            this.gender = "Male"
        }
        else{
            this.gender = "Female"
        }

        view.setDetails(name,age,this.gender,this.smoker,clientClass,currentStatus)
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
    fun setProfile(profile : Int)
    fun setLogo(image :  Int)
    fun setDetails(name : String,age : Int,gender : String,smoker : String,clientClass : String, status : String)
    fun setPremiumPrice(price : String)
    fun setPT(fee : Double)
}

interface BreakDownPresenter {
    fun processProfile()
    fun processLogo(id : Int)
    fun processDetails()
    fun processPremiumPrice(price : Double)
    fun processPT(data : QouteRequest.Result,index : Int)
}