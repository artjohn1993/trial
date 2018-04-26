package com.example.artjohn.blackfin.services

import com.example.artjohn.blackfin.model.Login
import com.example.artjohn.blackfin.model.SignIn
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

interface ApiServices {

    @Headers("content-type: application/json")
    @POST("LogIn")
    fun login(@Body signIn : SignIn) : Observable<Login.Signup>

    @POST("Logout")
    fun Logout() : Observable<Login.Signout>

}