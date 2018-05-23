package com.example.artjohn.blackfin.model

/**
 * Created by User on 25/04/2018.
 */
class Client {

    //region - Variables
    var clients : List<ClientsInformation>
    var userId : String
    var inputs : List<Inputs>
    var quoteId : Int
    //endregion

    constructor(clients : List<ClientsInformation>,
                userId : String,
                inputs : List<Inputs>,
                quoteId : Int) {
            this.clients  = clients
            this.userId = userId
            this.quoteId = quoteId
            this.inputs = inputs
        }
}

class ClientsInformation {

    //region - Variables
    var  name : String
    var isPrimary : Boolean
    var clientId : String
    var isSmoker : Boolean
    var age : String
    var gender : String
    var isChild : Boolean
    var employedStatus : String
    var occupationId : Int
    //endregion

    constructor(name : String,
                isPrimary : Boolean,
                clientId : String,
                isSmoker : Boolean,
                age : String,
                gender : String,
                isChild : Boolean,
                employedStatus : String,
                occupationId : Int) {
            this.name = name
            this.isPrimary = isPrimary
            this.clientId = clientId
            this.isSmoker = isSmoker
            this.age = age
            this.gender = gender
            this.isChild = isChild
            this.employedStatus = employedStatus
            this.occupationId = occupationId
        }
}

class Inputs {

    //region - Variables
    var clientId : Int
    var inputs : Benefits
    //endregion

    constructor(clientId : Int,
                inputs : Benefits) {
        this.clientId  = clientId
        this.inputs  = inputs
        }
}

class Benefits {

    //region - Variables
    var dentalOptical : Boolean
    var specialistsTest : Boolean
    var benefitPeriod : Int
    var calcPeriod : Int
    var isAccelerated : Boolean
    var gpPrescriptions : Boolean
    var frequency : Int
    var isLifeBuyback : Boolean
    var isTpdAddon : Boolean
    var benefitPeriodType : String
    var occupationType : String
    var wopWeekWaitPeriod : Int
    var isFutureInsurability : Boolean
    var booster : Boolean
    var excess : Int
    var coverAmount : Double
    var loading : Double
    var isTraumaBuyback : Boolean
    var benefitProductList : List<BenefitsProductList>
    var benefitsType : String
    //endregion

    constructor(dentalOptical : Boolean,
                specialistsTest : Boolean,
                benefitPeriod : Int,
                calcPeriod : Int,
                isAccelerated : Boolean,
                gpPrescriptions : Boolean,
                frequency : Int,
                isLifeBuyback : Boolean,
                isTpdAddon : Boolean,
                benefitPeriodType : String,
                occupationType : String,
                wopWeekWaitPeriod : Int,
                isFutureInsurability : Boolean,
                booster : Boolean,
                excess : Int,
                coverAmount : Double,
                loading : Double,
                isTraumaBuyback : Boolean,
                benefitProductList : List<BenefitsProductList>,
                benefitsType : String) {
        this.dentalOptical  = dentalOptical
        this.specialistsTest  = specialistsTest
        this.benefitPeriod = benefitPeriod
        this.calcPeriod = calcPeriod
        this.isAccelerated  = isAccelerated
        this.gpPrescriptions  = gpPrescriptions
        this.frequency  = frequency
        this.isLifeBuyback  = isLifeBuyback
        this.isTpdAddon  = isTpdAddon
        this.benefitPeriodType  = benefitPeriodType
        this.occupationType  = occupationType
        this.wopWeekWaitPeriod = wopWeekWaitPeriod
        this.isFutureInsurability  = isFutureInsurability
        this.booster = booster
        this.excess = excess
        this.coverAmount = coverAmount
        this.loading = loading
        this.isTraumaBuyback = isTraumaBuyback
        this.benefitProductList = benefitProductList
        this.benefitsType = benefitsType
        }
}

class BenefitsProductList {

    //region - Variables
    var benefitId : Int
    var productName : String
    var providerId : Int
    var providerName : String
    var wopCoverAmount : Int
    var productGroupId: Int
    //endregion

    constructor(benefitId : Int,
                productName : String,
                providerId : Int,
                providerName : String,
                wopCoverAmount : Int,
                productGroupId: Int) {
        this.benefitId  = benefitId
        this.productName  = productName
        this.providerId  = providerId
        this.providerName  = providerName
        this.wopCoverAmount = wopCoverAmount
        this.productGroupId = productGroupId
        }
}


