package com.example.artjohn.blackfin.event

import com.example.artjohn.blackfin.model.BenefitsProductList
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

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
    fun getListProduct(product : Product.List?,
                       provider : Provider.Result?,
                       benefitID: Int) : List<BenefitsProductList> {
        var  array : ArrayList<BenefitsProductList> = ArrayList()

        if (product != null) {
            for(index in 0..product.data.products.size - 1) {
                if(product.data.products[index].benefitId == benefitID) {
                    productGroupId = product.data.products[index].productGroupId
                    productName = product.data.products[index].productName
                    if (provider != null) {
                        for(index2 in 0..provider.data.providers.size - 1) {
                            if(product.data.products[index].providerId == provider.data.providers[index2].providerId) {
                                providerID = provider.data.providers[index2].providerId
                                providerName = provider.data.providers[index2].providerName
                            }
                        }
                    }
                    array.add(BenefitsProductList(benefitID,
                            productName,
                            providerID,
                            providerName,
                            0,
                            productGroupId))
                }
            }//end of first loop
            return array
        }

        else {
            return array
        }
    }
    //endregion
}