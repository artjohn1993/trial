package com.example.artjohn.blackfin.model

object UpdateQouteSetting {
    data class Body(
            var userId : String,
            var quoteId : Int,
            var records : List<Records>
    )
    data class Records(
            var providerId : Int,
            var benefitId : Int,
            var defaultProductGroupId : Int
    )
    data class Result(
            var data : Data
    )
    data class Data(
            var message : String,
            var status : String
    )
}