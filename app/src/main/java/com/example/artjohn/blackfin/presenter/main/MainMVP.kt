package com.example.artjohn.blackfin.presenter.main

import android.widget.ArrayAdapter

class MainMVP
{

    interface View
    {
        fun setAgeAdapter(data : ArrayList<Int>)
        fun setStatusAdapter(data : Array<String>)
        fun setOccupationAdapter(data : Array<String>)
        fun setGenderAdapter(data : Array<String>)
    }

    interface Presenter
    {
        fun processAgeAdapter()
        fun setAdapters()
        fun saveClientInfo(name : String, primary : Boolean, clientID : String ,smoker : Boolean, age : String,gender : String,isChild : Boolean, status : String, occupation : Int)
    }


    
}

