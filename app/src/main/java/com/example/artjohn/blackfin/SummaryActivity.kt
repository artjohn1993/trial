package com.example.artjohn.blackfin

import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import com.example.artjohn.blackfin.adapter.SummaryAdapter
import com.example.artjohn.blackfin.adapter.SummaryAdapterTwo
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.event.*
import com.example.artjohn.blackfin.model.*
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_benefits.*
import kotlinx.android.synthetic.main.activity_summary.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.alert
import java.util.*

class SummaryActivity : BaseActivity() {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        title = "Result"
        summaryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        summaryNotAvailableRecylerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        checkConfigureBenefits()

    }

    override fun onResume() {
        super.onResume()
        checkConfigureBenefits()
    }

    fun getClient() : Client
    {

        var data = Client(ClientInfo.array,"f11f217f-f500-4c36-96bd-a361df4009ac",ConfigureBenefits.array,0)
        return data
    }

    fun request(data : Client)
    {

       compositeDisposable?.add(
            apiServer.requestQoutes(data)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe({
                        result ->
                        showVisibility()
                        summaryRecyclerView.adapter = SummaryAdapter(result)

                        Collections.sort(result.data.data.result.providers, object : Comparator<QouteRequest.ProviderList> {
                            override fun compare(obj1: QouteRequest.ProviderList, obj2: QouteRequest.ProviderList): Int {
                                return obj1.errorSummary.size.compareTo(obj2.errorSummary.size)
                            }
                        })

                        summaryNotAvailableRecylerView.adapter = SummaryAdapterTwo(result)
                    },{
                        error ->
                        print(error.message)
                        summaryProgressBar.visibility = View.GONE
                    })
    )

    }


    fun showVisibility(){
        summaryProgressBar.visibility = View.GONE
        summarySaveButton.visibility = View.VISIBLE
        monthlyWrapper.visibility =  View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConfigureBenefits(event : PremiumRange)
    {
        summaryTotalText.text =  event.totalProvider.toString() + " Results. " + "$" + event.min + " - " + "$" + event.max
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSummaryNotAvailable(event : SummaryNotAvailable)
    {
            summaryNotAvailableRecylerView.visibility = View.VISIBLE
            summaryNotAvailableText.text = "No Result Available:"
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSummaryAvailable(event : SummaryAvailable)
    {
            summaryRecyclerView.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onErrorEvent(event : ErrorEvent)
    {
        alert(event.message){  }.show()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProductPremium(event : ProductPremium)
    {
        var intent = Intent(this,BreakdownActivity::class.java)
        intent.putExtra("clientInfo", event.clientInfo)
        intent.putExtra("qoute",event.qoute)
        intent.putExtra("position",event.position)
        startActivity(intent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCheckRecyclerView(event : CheckRecyclerView)
    {
        try {
            summaryRecyclerView.adapter.itemCount
            summaryNotAvailableRecylerView.adapter.itemCount

        }catch (e : Exception)
        {
            summaryProgressBar.visibility = View.VISIBLE
            var data = Client(ClientInfo.array,"f11f217f-f500-4c36-96bd-a361df4009ac",ConfigureBenefits.array,0)
            request(data)
        }
    }
    fun checkConfigureBenefits()
    {
        if(ConfigureBenefits.array.isEmpty())
        {
            summaryProgressBar.visibility = View.GONE

            Handler().postDelayed({
                showWarning()
            },2000)
        }
        else{
            hideWarning()
            request(getClient())
        }

    }
    fun showWarning()
    {

        warningImage.visibility = View.VISIBLE
        warningText.visibility = View.VISIBLE
    }
    fun hideWarning()
    {
        warningImage.visibility = View.GONE
        warningText.visibility = View.GONE
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
