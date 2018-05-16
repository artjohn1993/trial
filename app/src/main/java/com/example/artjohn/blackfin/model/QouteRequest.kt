package com.example.artjohn.blackfin.model

import okhttp3.Response

/**
 * Created by User on 27/04/2018.
 */
object QouteRequest
{
    data class Result
    (
            var data : Data
    )

    data class Data
    (
            var userId : String,
            var quoteId : Int,
            var data : DataResult
    )
    data class DataResult
    (
            var result : ProviderResut,
            var crunchTimeQuoteEngine : Double,
            var crunchTimeFidelity : Double,
            var apiLastUpdated : String
    )
    data class ProviderResut
    (
            var providers : MutableList<ProviderList>
    )
    data class ProviderList
    (
        var providerId : Int,
        var providerName : String,
        var clientBreakdown : MutableList<ClientBreakDown>,
        var policyFee : Double,
        var totalPremium : Double,
        var errorSummary : MutableList<ErrorSummary>,
        var containsError : Boolean
    )
    data class ErrorSummary
    (
            var errorId : Int,
            var errorMessage: String
    )
    data class ClientBreakDown
    (
            var clientId : String,
            var productPremiums : MutableList<ProductPremium>
    )
    data class ProductPremium
    (
            var benefitId : Int,
            var benefitName : String,
            var productId : Int,
            var productGroupId : Int,
            var productName : String,
            var premium : Double,
            var hasPremium : Boolean,
            var hasWopCover : Boolean,
            var coverDetail : String,
            var description : String,
            var errorId : Int,
            var errorMessage : String
    )
}

