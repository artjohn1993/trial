package com.example.artjohn.blackfin.event

/**
 * Created by User on 26/04/2018.
 */
class LoadingPercentage {
    //region - Variables
    var default : Double = 1.0
    var percent : Double
    //endregion

    constructor(percent : Double) {
        this.percent = percent
    }

    //region - Public methods
    fun calculate() : Double {
        var result = percent * 0.01
        return result.plus(default)
    }
    //endregion
}