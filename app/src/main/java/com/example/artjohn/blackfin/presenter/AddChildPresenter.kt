package com.example.artjohn.blackfin.presenter

import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.ClientsInformation

class AddChildClass(var view : AddChildView) : AddChildPresenter {

    var gender = arrayOf(
            "Male",
            "Female")

    override fun setAdapter() {
        processAge()
        processGender()
    }
    override fun processAge() {
        val age = ArrayList<Int>()
        for(i in 1..17) {
            age.add(i)
        }
        view.setAge(age)
    }

    override fun processGender() {
        view.setGender(gender)
    }

    override fun saveClientInfo(name: String, primary: Boolean, clientID: String, smoker: Boolean, age: String, gender: String, isChild: Boolean, status: String, occupation: Int) {
        var qoute  = ClientsInformation(
                name,
                primary,
                clientID,
                smoker,
                age,
                gender,
                isChild,
                status,
                occupation)
        ClientInfo(qoute)
    }
}
interface AddChildView {
    fun setAge(data : ArrayList<Int>)
    fun setGender(data : Array<String>)
}
interface AddChildPresenter {
    fun setAdapter()
    fun processGender()
    fun processAge()
    fun saveClientInfo(name : String,
                       primary : Boolean,
                       clientID : String,
                       smoker : Boolean,
                       age : String,
                       gender : String,
                       isChild : Boolean,
                       status : String,
                       occupation : Int)
}
