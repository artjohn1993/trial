package com.example.artjohn.blackfin.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.SummaryAdapter
import com.example.artjohn.blackfin.adapter.SummaryAdapterTwo
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.dialog.LoadingDialog
import com.example.artjohn.blackfin.event.*
import com.example.artjohn.blackfin.model.*
import com.example.artjohn.blackfin.presenter.SummaryPresenter
import com.example.artjohn.blackfin.presenter.SummaryPresenterClass
import com.example.artjohn.blackfin.presenter.SummaryView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_summary.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.alert

class SummaryActivity : BaseActivity(),
        SummaryView {

    // region - Variables
    var loading = LoadingDialog(this)
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }

    val presenter : SummaryPresenter = SummaryPresenterClass(this, apiServer)
    // endregion

    // region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        title = "Result"
        this.initRecyclerViews()
        presenter.processFrequency()

        frequencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                loading.show()
                presenter.processChangeFrequency(position)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    // endregion

    // region - Private methods
    private fun getClient() : Client {
        var data = Client(ClientInfo.array,
                "f11f217f-f500-4c36-96bd-a361df4009ac",
                ConfigureBenefits.array,
                0)
        return data
    }

    private fun checkConfigureBenefits() {
        if(ConfigureBenefits.array.isEmpty()) {
            loading.hide()
            Handler().postDelayed({
                showWarning()
            },2000)
        }
        else {
            hideWarning()
            presenter.processRecyclerAdapter(getClient())
        }
    }

    private fun showWarning() {
        warningImage.visibility = View.VISIBLE
        warningText.visibility = View.VISIBLE
    }

    private fun hideWarning() {
        warningImage.visibility = View.GONE
        warningText.visibility = View.GONE
    }

    private fun showVisibility() {
        loading.hide()
        summarySaveButton.visibility = View.VISIBLE
        monthlyWrapper.visibility =  View.VISIBLE
    }

    private fun initRecyclerViews() {
        summaryRecyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        summaryNotAvailableRecylerView.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
    }
    // endregion

    // region - Eventbus methods
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConfigureBenefits(event : PremiumRange) {
        summaryTotalText.text =  event.totalProvider.toString() + " Results. " + "$" + event.min + " - " + "$" + event.max
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSummaryNotAvailable(event : SummaryNotAvailable) {
        summaryNotAvailableRecylerView.visibility = View.VISIBLE
        summaryNotAvailableText.text = "No Result Available:"
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSummaryAvailable(event : SummaryAvailable) {
        summaryRecyclerView.visibility = View.VISIBLE
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onErrorEvent(event : ErrorEvent) {
        alert(event.message){  }.show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProductPremium(event : ProductPremium) {
        var intent = Intent(this, BreakdownActivity::class.java)
        intent.putExtra("clientInfo", event.clientInfo)
        intent.putExtra("qoute",event.qoute)
        intent.putExtra("position",event.position)
        startActivity(intent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCheckRecyclerView(event : CheckRecyclerView) {
        try {
            summaryRecyclerView.adapter.itemCount
            summaryNotAvailableRecylerView.adapter.itemCount

        }catch (e : Exception) {
            loading.show()
            checkConfigureBenefits()
        }
    }
    // endregion

    // region - Presenter Delegates
    override fun setAdapter(data: QouteRequest.Result, dataSorted: QouteRequest.Result)
    {
        showVisibility()
        summaryRecyclerView.adapter = SummaryAdapter(data)
        summaryNotAvailableRecylerView.adapter = SummaryAdapterTwo(dataSorted)
    }

    override fun requestFailed()
    {
        loading.hide()
    }

    override fun setFrequencySpinner(data: Array<String>) {
        val frequencyAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        frequencySpinner.adapter = frequencyAdapter
        frequencySpinner.setSelection(1)
    }
    override fun updatedFrequency() {
        checkConfigureBenefits()
    }
    // endregion
}