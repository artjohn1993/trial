package com.example.artjohn.blackfin.model

class SignIn {
    //region - Variables
    var email : String
    var password : String
    var rememberMe : Boolean
    //endregion

    constructor(email : String, password: String,rememberMe : Boolean) {
        this.email = email
        this.password = password
        this.rememberMe = rememberMe
    }
}