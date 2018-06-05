package com.example.artjohn.blackfin.event

import com.example.artjohn.blackfin.model.Benefits
import com.example.artjohn.blackfin.model.BenefitsProductList
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.Inputs
import org.greenrobot.eventbus.EventBus

class ConfiguredBenefits {
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
    var benefitsProduct : List<BenefitsProductList>
    var benefitsType : String
    var id : Int
    var benefitsID : Int

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
                benefitsProduct : List<BenefitsProductList>,
                benefitsType : String,
                id : Int,
                benefitsID : Int) {
        this.dentalOptical = dentalOptical
        this.specialistsTest = specialistsTest
        this.benefitPeriod = benefitPeriod
        this.calcPeriod = calcPeriod
        this.isAccelerated = isAccelerated
        this.gpPrescriptions = gpPrescriptions
        this.frequency = frequency
        this.isLifeBuyback = isLifeBuyback
        this.isTpdAddon = isTpdAddon
        this.benefitPeriodType = benefitPeriodType
        this.occupationType = occupationType
        this.wopWeekWaitPeriod = wopWeekWaitPeriod
        this.isFutureInsurability = isFutureInsurability
        this.booster = booster
        this.excess = excess
        this.coverAmount = coverAmount
        this.loading = loading
        this.isTraumaBuyback =  isTraumaBuyback
        this.benefitsProduct = benefitsProduct
        this.benefitsType = benefitsType
        this.id = id
        this.benefitsID = benefitsID

        val data = Benefits(this.dentalOptical,
                this.specialistsTest,
                this.benefitPeriod,
                this.calcPeriod,
                this.isAccelerated,
                this.gpPrescriptions,
                this.frequency,
                this.isLifeBuyback,
                this.isTpdAddon,
                this.benefitPeriodType,
                this.occupationType,
                this.wopWeekWaitPeriod,
                this.isFutureInsurability,
                this.booster,
                this.excess,
                this.coverAmount,
                this.loading,
                this.isTraumaBuyback,
                this.benefitsProduct,
                this.benefitsType
        )
        var inputs = Inputs(this.id, data)
        if (ConfigureBenefits.array.isEmpty()) {
            EventBus.getDefault().post(ConfigureBenefits(inputs))
        }
        else {
            var exist : Boolean = false
            for (x in 0 until ConfigureBenefits.array.size) {
                if (ConfigureBenefits.array[x].clientId == id && ConfigureBenefits.array[x].inputs.benefitProductList[0].benefitId == this.benefitsID) {
                    ConfigureBenefits.array[x] = inputs
                    exist = true
                    break
                }
            }
            if (!exist) {
                EventBus.getDefault().post(ConfigureBenefits(inputs))
            }
        }
    }
}