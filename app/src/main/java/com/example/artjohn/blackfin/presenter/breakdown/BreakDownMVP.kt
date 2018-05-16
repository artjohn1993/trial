package com.example.artjohn.blackfin.presenter.breakdown

import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.QouteRequest

class BreakDownMVP
{
    interface View
    {
        fun setProfile(profile : Int)
        fun setLogo(image :  Int)
        fun setDetails(name : String,age : Int,gender : String,smoker : String,clientClass : String, status : String)
        fun setPremiumPrice(price : String)
        fun setPT(fee : Double)
    }
    interface Presenter
    {
        fun processProfile()
        fun processLogo(id : Int)
        fun processDetails()
        fun processPremiumPrice(price : Double)
        fun processPT(data : QouteRequest.Result,index : Int)
    }
}