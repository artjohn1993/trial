package com.example.artjohn.blackfin.api

import android.content.Context
import com.example.artjohn.blackfin.model.UserInformation
import com.example.artjohn.blackfin.services.ApiServices
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*


class CustomHttp{
    companion object {
        fun createOkhttp() : OkHttpClient{
            val okhttp = OkHttpClient.Builder()

            val interceptor = Interceptor { chain ->
                var request: Request? = null
                var token = UserInformation().returnToken()
                request = chain?.request()?.newBuilder()
                        ?.addHeader("Accept", "application/json")
                        ?.addHeader("Authorization", "Bearer $token")
                        ?.addHeader("content-type","application/json")
                        ?.build()

                chain.proceed(request)
            }
            okhttp.networkInterceptors().add(interceptor)
            okhttp.protocols(Arrays.asList(Protocol.HTTP_1_1))
            return okhttp.build()
        }
    }
}

class BlackfinApi {

    companion object {
        fun create(context: Context) : ApiServices{
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .baseUrl("https://staging.blackfin.technology/mobile/")
                    .client(CustomHttp.createOkhttp())
                    .build()

            return retrofit.create(ApiServices::class.java)
        }
    }
}