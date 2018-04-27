package com.example.artjohn.blackfin.model

/**
 * Created by User on 27/04/2018.
 */
object Product {
    data class List(
            var data : Data
    )
    data class Data(
            var products : kotlin.collections.List<Products>
    )
    data class Products(
            var productGroupId : Int,
            var providerId : Int,
            var benefitId : Int,
            var productName : String
    )
}