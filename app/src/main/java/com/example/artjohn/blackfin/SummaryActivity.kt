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
import com.example.artjohn.blackfin.presenter.summary.SummaryMVP
import com.example.artjohn.blackfin.presenter.summary.SummaryPresenter
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

class SummaryActivity : BaseActivity(),SummaryMVP.View
{


    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    val presenter : SummaryPresenter = SummaryPresenter(this,apiServer)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        title = "Result"
        summaryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        summaryNotAvailableRecylerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
    }

    private fun getClient() : Client
    {
        var data = Client(ClientInfo.array,"f11f217f-f500-4c36-96bd-a361df4009ac",ConfigureBenefits.array,0)
        return data
    }

    private fun checkConfigureBenefits()
    {
        if(ConfigureBenefits.array.isEmpty())
        {
            summaryProgressBar.visibility = View.GONE
            Handler().postDelayed({
                showWarning()
            },2000)
        }
        else
        {
            hideWarning()
            presenter.processRecyclerAdapter(getClient())
        }
    }
    private fun showWarning()
    {

        warningImage.visibility = View.VISIBLE
        warningText.visibility = View.VISIBLE
    }
    private fun hideWarning()
    {
        warningImage.visibility = View.GONE
        warningText.visibility = View.GONE
    }
    private fun showVisibility()
    {
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
        try
        {
            summaryRecyclerView.adapter.itemCount
            summaryNotAvailableRecylerView.adapter.itemCount

        }catch (e : Exception)
        {
            summaryProgressBar.visibility = View.VISIBLE
            checkConfigureBenefits()
        }
    }

    public override fun onStart()
    {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop()
    {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    override fun onPause()
    {
        super.onPause()
        compositeDisposable.clear()

    }
    override fun setAdapter(data: QouteRequest.Result, dataSorted: QouteRequest.Result)
    {
        showVisibility()
        summaryRecyclerView.adapter = SummaryAdapter(data)
        summaryNotAvailableRecylerView.adapter = SummaryAdapterTwo(dataSorted)
    }

    override fun requestFailed()
    {
        summaryProgressBar.visibility = View.GONE
    }
}