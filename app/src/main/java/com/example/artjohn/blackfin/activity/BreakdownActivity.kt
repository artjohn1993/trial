package com.example.artjohn.blackfin.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.BreakdownAdapter
import com.example.artjohn.blackfin.adapter.PremiumAdapter

import com.example.artjohn.blackfin.model.QouteRequest
import com.example.artjohn.blackfin.event.TotalPremium
import com.example.artjohn.blackfin.presenter.BreakDownPresenterClass
import com.example.artjohn.blackfin.presenter.BreakDownView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_breakdown.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class BreakdownActivity : AppCompatActivity(),
        BreakDownView {

    //region - Variables
    lateinit var qoute : QouteRequest.Result
    var index : Int = 0
    var id  : Int = 0
    var totalPremium : Double = 0.0
    val presenter = BreakDownPresenterClass(this)
    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breakdown)
        title = "Breakdown"
        qoute = Gson().fromJson(
                intent.extras.get("qoute").toString(),
                QouteRequest.Result::class.java)
        index = intent.extras.get("position") as Int
        id   = qoute.data.data.result.providers[index].providerId

        this.bind()
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    //endregion

    //region - Private method
    private fun bind() {
        presenter.processLogo(id)
        presenter.processPT(qoute,index)
        setRecyclerLayout()
    }
    private fun setRecyclerLayout() {
        breakMainRecyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        breakMainRecyclerView.adapter = BreakdownAdapter(qoute,
                index,
                baseContext)
    }
    //endregion

    //region - Eventbus
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProductPremium(event : TotalPremium) {
        totalPremium += event.total
        presenter.processPremiumPrice(totalPremium)
    }
    //endregion

    //region - Presenter Delegates
    override fun setLogo(image :  Int) {
        logo.setImageResource(image)
    }

    @SuppressLint("SetTextI18n")
    override fun setPremiumPrice(price: String) {
        premiumPriceText.text = "$$price"
    }

    @SuppressLint("SetTextI18n")
    override fun setPT(fee: Double) {
        policyPriceText.text = "$$fee"
        totalPremium += fee
    }
    //endregion
}

