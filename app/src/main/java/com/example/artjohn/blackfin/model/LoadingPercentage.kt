package com.example.artjohn.blackfin.model

/**
 * Created by User on 26/04/2018.
 */
class LoadingPercentage {
    var default : Double = 1.0
    var percent : Double

    constructor(percent : Double)
    {
        this.percent = percent
    }

    fun calculate() : Double{
        var result = percent * 0.01

        return result.plus(default)
    }
}