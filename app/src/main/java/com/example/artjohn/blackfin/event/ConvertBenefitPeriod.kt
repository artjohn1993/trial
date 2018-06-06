package com.example.artjohn.blackfin.event

class ConvertBenefitPeriod {
    companion object {
        var convertedData : Int = 0
        fun value(data : String) : Int{
            when(data) {
                "year 1" -> {
                    convertedData = 1
                }
                "year 2" -> {
                    convertedData = 2
                }
                "year 5" -> {
                    convertedData = 5
                }
                "age 65" -> {
                    convertedData = 65
                }
                "age 70" -> {
                    convertedData = 70
                }
                else -> {
                    convertedData = 0
                }
            }
            return convertedData
        }
    }
}