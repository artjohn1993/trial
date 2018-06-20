package com.example.artjohn.blackfin.model

object UpdateQouteSettingByProvider {
    data class Body(
            var userId : String,
            var quoteId : Int,
            var records : List<Records>
    )
    data class Records(
            var providerId : Int,
            var providerStatus : Boolean
    )
    data class Result(
            var data : Data
    )
    data class Data(
            var code : Int,
            var status : String,
            var message : String
    )
}
