package com.example.artjohn.blackfin.event

class CalculatedPeriod {
    companion object {
        var period: Int = 0

        fun convert(period: String): Int {
            when (period) {
                "Yearly Renewable" -> {
                    this.period = 1
                }
                "Level (10 Years)" -> {
                    this.period = 10
                }
                "Level (15 Years)" -> {
                    this.period = 15
                }
                "Level (To Age 65)" -> {
                    this.period = 65
                }
                "Level (To Age 70)" -> {
                    this.period = 70
                }
                "Level (To Age 80)" -> {
                    this.period = 80
                }
                "Level (To Age 85)" -> {
                    this.period = 85
                }
                "Level (To Age 90)" -> {
                    this.period = 90
                }
                "Level (To Age 100)" -> {
                    this.period = 100
                }
            }
            return this.period
        }
    }
}
