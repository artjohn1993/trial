package com.example.artjohn.blackfin.model

/**
 * Created by User on 25/04/2018.
 */
class ConfigureBenefits {
    companion object {
        var array : ArrayList<Inputs> = ArrayList()
        var id : ArrayList<Int> = ArrayList()
    }

    constructor(array : Inputs) {
        ConfigureBenefits.array.add(array)
    }
}