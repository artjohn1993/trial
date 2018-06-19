package com.example.artjohn.blackfin.model

object QouteSettings {
    data class Result(
            var data : Data
    )
    data class Data(
            var userId : String,
            var providers : List<Providers>
    )
    data class Providers(
            var providerId : Int,
            var providerName : String,
            var providerStatus : Boolean,
            var benefits : List<QouteSettings.Benefits>
    )
    data class Benefits(
            var benefitId : Int,
            var benefitName : String,
            var product : QouteSettings.Product
    )
    data class Product(
            var defaultProductGroupId : Int,
            var productName : String
    )

    data class Body(
            var userId : String,
            var quoteId : Int
    )
}