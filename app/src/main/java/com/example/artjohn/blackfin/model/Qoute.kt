package com.example.artjohn.blackfin.model

/**
 * Created by User on 25/04/2018.
 */
object Qoute {
    data class Client(
            var clients : List<ClientsInformation>
    )
    data class ClientsInformation(
            var  name : String,
            var isPrimary : Boolean,
            var clientId : String,
            var isSmoker : Boolean,
            var age : String,
            var gender : String,
            var isChild : Boolean,
            var employedStatus : String,
            var occupationId : Int
    )

}