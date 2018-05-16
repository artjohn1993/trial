package com.example.artjohn.blackfin.model

object Login {
    data class Signup(
        var data : Data
    )

    data class Data(
            var response : Response,
            var authorization : Authorization,
            var user : User
    )

    data class Response(
            var message : String
    )

    data class Authorization(
            var token : String
    )

    data class User(
            var id : String,
            var email : String,
            var fspr : String,
            var fullname : String
    )

    data class Signout(
            var message : String
    )
}