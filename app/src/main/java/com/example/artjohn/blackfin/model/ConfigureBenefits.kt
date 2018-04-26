package com.example.artjohn.blackfin.model

/**
 * Created by User on 25/04/2018.
 */
class ConfigureBenefits {

    companion object {
        var array : ArrayList<Qoute.Inputs> = ArrayList()
        var id : ArrayList<Int> = ArrayList()
    }


    constructor(array : Qoute.Inputs)
    {
        ConfigureBenefits.array.add(array)
    }

}