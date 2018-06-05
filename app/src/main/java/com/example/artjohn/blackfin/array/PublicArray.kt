package com.example.artjohn.blackfin.array

class PublicArray {
    companion object {
        var loading = arrayOf (
                "0%",
                "50%",
                "75%",
                "100%",
                "125%",
                "150%",
                "175%",
                "200%",
                "250%",
                "300%",
                "400%",
                "500%"
        )
        var calPeriod = arrayOf (
                "Yearly Renewable",
                "Level (10 Years)",
                "Level (15 Years)",
                "Level (To Age 65)",
                "Level (To Age 70)",
                "Level (To Age 80)",
                "Level (To Age 85)",
                "Level (To Age 90)",
                "Level (To Age 100)"
        )
        var excess = arrayOf (
                "Nil - Excess",
                "250 Excess",
                "500 Excess",
                "1000 Excess",
                "2000 Excess",
                "3000 Excess",
                "4000 Excess",
                "6000 Excess",
                "10000 Excess"
        )
        var benefitPeriod = arrayOf(
                "Fixed Term",
                "To Age"
        )
        var policyType = arrayOf(
                "percent55",
                "percent75"
        )
    }
}