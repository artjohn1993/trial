package com.example.artjohn.blackfin.model

/**
 * Created by User on 25/04/2018.
 */
object Qoute {
    data class Client(
            var clients : List<ClientsInformation>,
            var userId : String,
            var quoteId : Int,
            var inputs : List<Inputs>
    )
    data class ClientsInformation(
            var  name : String,
            var isPrimary : Boolean,
            var clientId : String,
            var isSmoker : Boolean,
            var age : String,
            var gender : String,
            var isChild : Boolean,
            var employedStatus : String,
            var occupationId : Int
    )
    data class Inputs(
            var clientId : Int,
            var inputs : Benefits
    )
    data class Benefits(
            var dentalOptical : Boolean,
            var specialistsTest : Boolean,
            var benefitPeriod : Int,
            var calcPeriod : Int,
            var isAccelerated : Boolean,
            var gpPrescriptions : Boolean,
            var frequency : Int,
            var isLifeBuyback : Boolean,
            var isTpdAddon : Boolean,
            var benefitPeriodType : String,
            var occupationType : String,
            var wopWeekWaitPeriod : Int,
            var isFutureInsurability : Boolean,
            var booster : Boolean,
            var excess : Int,
            var coverAmount : Double,
            var loading : Double,
            var isTraumaBuyback : Boolean
    )
}