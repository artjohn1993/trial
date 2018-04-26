package com.example.artjohn.blackfin.model

/**
 * Created by User on 25/04/2018.
 */
class ConfigureBenefits {

    /*var dentalOptical : Boolean? = null
    var specialistsTest : Boolean? = null
    var benefitPeriod : Int? = null
    var calcPeriod : Int? = null
    var isAccelerated : Boolean? = null
    var gpPrescriptions : Boolean? = null
    var frequency : Int? = null
    var isLifeBuyback : Boolean? = null
    var isTpdAddon : Boolean? = null
    var benefitPeriodType : String? = null
    var occupationType : String? = null
    var wopWeekWaitPeriod : Int? = null
    var isFutureInsurability : Boolean? = null
    var booster : Boolean? = null
    var excess : Int? = null
    var coverAmount : Double? = null
    var loading : Double? = null
    var isTraumaBuyback : Boolean? = null*/

    companion object {
        var array : ArrayList<Qoute.Inputs>? = null
    }


    constructor(array : ArrayList<Qoute.Inputs>)
    {
        ConfigureBenefits.array = array
    }

}