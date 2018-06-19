package com.example.artjohn.blackfin.event

import com.example.artjohn.blackfin.model.BenefitsProductList
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider
import com.example.artjohn.blackfin.model.QouteSettings

/**
 * Created by User on 27/04/2018.
 */
class ProcessProduct {
    //region - Variables
    var productGroupId  : Int = 0
    var productName : String = ""
    var providerID : Int = 0
    var providerName : String = ""
    //endregion

    //region - Public methods
    fun getListProduct(data : QouteSettings.Result,
                       benefitID: Int) : List<BenefitsProductList> {
        var  array : ArrayList<BenefitsProductList> = ArrayList()

        for(index in 0 until data.data.providers.size) {
            for (index2 in 0 until data.data.providers[index].benefits.size) {
                if (benefitID == data.data.providers[index].benefits[index2].benefitId && data.data.providers[index].providerStatus) {
                    productName = data.data.providers[index].benefits[index2].product.productName
                    providerID = data.data.providers[index].providerId
                    providerName = data.data.providers[index].providerName
                    productGroupId = data.data.providers[index].benefits[index2].product.defaultProductGroupId
                    array.add(BenefitsProductList(benefitID,
                            productName,
                            providerID,
                            providerName,
                            0,
                            productGroupId))
                }
            }
        }
        return array
    }
    //endregion
}