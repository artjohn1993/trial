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
        var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkcm9pZDEtcWF0ZXN0QG1haWwuY29tIiwianRpIjoiZTczYTEwMzctZjlkMS00NDJlLTg4NjgtNTNjNmRjZjMzYjJhIiwiZXhwIjoxNTI3NDE2OTIzLCJpc3MiOiJodHRwOi8vYmxhY2tmaW4udGVjaG5vbG9neSIsImF1ZCI6Imh0dHA6Ly9ibGFja2Zpbi50ZWNobm9sb2d5In0.kvAgh3cLejmjLb7066G-reHjNvxOcwi27CIFde-DR70"
        fun createOkhttp() : OkHttpClient{
            val okhttp = OkHttpClient.Builder()

            val interceptor = Interceptor { chain ->
                var request: Request? = null

                if(!UserInformation.array.size.equals(0))
                {
                    token = UserInformation.array[0].data.authorization.token
                }

                request = chain?.request()?.newBuilder()
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