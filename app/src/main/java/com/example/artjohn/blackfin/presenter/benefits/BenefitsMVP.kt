package com.example.artjohn.blackfin.presenter.benefits

import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider

class BenefitsMVP
{
    interface View
    {
        fun setAdapter(product : Product.List?, provider : Provider.Result?)
    }
    interface Presenter
    {
        fun processAdapter()
        fun getProduct()
        fun getProvider(value : Product.List)
    }

}