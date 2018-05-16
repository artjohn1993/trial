package com.example.artjohn.blackfin.services

import com.example.artjohn.blackfin.model.*
import com.squareup.moshi.Json
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

interface ApiServices
{

    @Headers("content-type: application/json")
    @POST("LogIn")
    fun login(@Body signIn : SignIn) : Observable<Login.Signup>

    @POST("Logout")
    fun Logout() : Observable<Login.Signout>

    @GET("product")
    fun getProduct() : Observable<Product.List>

    @GET("provider")
    fun getProvider() : Observable<Provider.Result>

    @Headers("content-type: application/json")
    @POST("quote/request-quote")
    fun requestQoutes(@Body client : Client) : Observable<QouteRequest.Result>
}