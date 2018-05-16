package com.example.artjohn.blackfin.model

/**
 * Created by User on 27/04/2018.
 */
object Provider
{
    data class Result
    (
            var data : Data
    )
    data class Data
    (
            var providers : List<Providers>
    )
    data class Providers
    (
            var providerId : Int,
            var providerName : String,
            var status : Boolean
    )
}