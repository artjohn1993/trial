package com.example.artjohn.blackfin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.api.CustomHttp
import com.example.artjohn.blackfin.model.*
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class SummaryActivity : AppCompatActivity() {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    private  var userID : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary2)

        println(ClientInfo.array.size)
        println(ConfigureBenefits.array.size)
        println(ConfigureBenefits.array[0].inputs.benefitProductList.size)

       var data = Client(ClientInfo.array,"f11f217f-f500-4c36-96bd-a361df4009ac",0,ConfigureBenefits.array)

        var moshi = Moshi.Builder().build()

        var adapter = moshi.adapter(Client::class.java)
        var json  = adapter.toJson(data)
        println(json)

    }

    override fun onResume() {
        super.onResume()
        var data = Client(ClientInfo.array,"f11f217f-f500-4c36-96bd-a361df4009ac",0,ConfigureBenefits.array)
        request(data)
    }

    fun request(data : Client)
    {

       compositeDisposable?.add(
            apiServer.requestQoutes(data)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe({
                        result ->
                    println("---------------------==============REQUEST RESULT==================----------------------------")
                        println(result.data.response.status)
                    },{
                        error ->
                        print(error.toString())
                    })
    )

    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()

    }
}
