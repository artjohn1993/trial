package com.example.artjohn.blackfin.model

class SignIn
{

    var email : String
    var password : String
    var rememberMe : Boolean

    constructor(email : String, password: String,rememberMe : Boolean)
    {
        this.email = email
        this.password = password
        this.rememberMe = rememberMe

    }
}