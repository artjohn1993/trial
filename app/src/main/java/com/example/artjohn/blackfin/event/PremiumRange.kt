package com.example.artjohn.blackfin.event

class PremiumRange {
    var min : Double
    var max : Double
    var totalProvider : Int

    constructor(min : Double,max : Double,total : Int) {
        this.max = max
        this.min = min
        this.totalProvider = total
    }
}