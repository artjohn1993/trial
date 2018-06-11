package com.example.artjohn.blackfin.event

import android.graphics.Color
import com.example.artjohn.blackfin.model.ConfigureBenefits

class RemoveConfiguredBenefits {
    var userID : Int
    var benefitID : Int

    constructor(userID : Int, benefitID : Int) {
        this.userID = userID
        this.benefitID = benefitID
        for (x in 0 until ConfigureBenefits.array.size) {
            if (ConfigureBenefits.array[x].clientId == this.userID  && ConfigureBenefits.array[x].inputs.benefitProductList[0].benefitId == this.benefitID) {
                ConfigureBenefits.array.removeAt(x)
                break
            }
        }
    }
}