package com.example.artjohn.blackfin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.artjohn.blackfin.adapter.SummaryAdapter
import com.example.artjohn.blackfin.adapter.SummaryAdapterTwo
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.api.CustomHttp
import com.example.artjohn.blackfin.model.*
import com.google.gson.Gson
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_benefits.*
import kotlinx.android.synthetic.main.activity_summary.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import org.xml.sax.Parser

class SummaryActivity : AppCompatActivity() {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    private  var userID : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        title = "Result"
        summaryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        summaryNotAvailableRecylerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)

       var data = Client(ClientInfo.array,"f11f217f-f500-4c36-96bd-a361df4009ac",ConfigureBenefits.array,0)
        request(data)
    println(Gson().toJson(data))
    }

    override fun onResume() {
        super.onResume()
        var data = Client(ClientInfo.array,"f11f217f-f500-4c36-96bd-a361df4009ac",ConfigureBenefits.array,0)
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

                        println(Gson().toJson(result))
                        showVisibility()
                        summaryRecyclerView.adapter = SummaryAdapter(result)
                        summaryNotAvailableRecylerView.adapter = SummaryAdapterTwo(result)
                    },{
                        error ->
                        print(error.message)
                        print(error.stackTrace)
                        summaryProgressBar.visibility = View.GONE
                    })
    )

    }
    fun showVisibility(){
        summaryProgressBar.visibility = View.GONE
        summarySaveButton.visibility = View.VISIBLE
        monthlyWrapper.visibility =  View.VISIBLE
        summaryTotalText.visibility = View.VISIBLE
        summaryNotAvailableText.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConfigureBenefits(event : PremiumRange)
    {
        summaryTotalText.text =  event.totalProvider.toString() + " Results. " + "$" + event.min + " - " + "$" + event.max
    }




    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()

    }

}
