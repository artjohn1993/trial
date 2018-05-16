package com.example.artjohn.blackfin.presenter.main

import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.ClientsInformation
import kotlinx.android.synthetic.main.activity_main.*

class MainPresenter(var view : MainMVP.View) : MainMVP.Presenter
{
    override fun saveClientInfo(name : String, primary : Boolean, clientID : String ,smoker : Boolean, age : String,gender : String,isChild : Boolean, status : String, occupation : Int) {


        var qoute  = ClientsInformation(name,primary,clientID,smoker,age,gender,isChild,status,occupation)

        ClientInfo(qoute)
    }

    override fun setAdapters()
    {
        var status = arrayOf("Employed","Self-Employed","Self-Employed < 3 years","Non-Earner")
        var occupation = arrayOf("Class 1","Class 2","Class 3","Class 4","Class 5")
        var gender = arrayOf("Male","Female")
        view.setStatusAdapter(status)
        view.setOccupationAdapter(occupation)
        view.setGenderAdapter(gender)
    }



    override fun processAgeAdapter()
    {
        val age = ArrayList<Int>()
        for(i in 0..75)
        {
            age.add(i)
        }
        view.setAgeAdapter(age)
    }


}