package com.example.artjohn.blackfin

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.GridLayout
import com.example.artjohn.blackfin.adapter.BenefitsAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.api.CustomHttp
import com.example.artjohn.blackfin.dialog.HealthDialog
import com.example.artjohn.blackfin.dialog.LifeDialog
import com.example.artjohn.blackfin.event.BenefitsProgressBar
import com.example.artjohn.blackfin.event.CheckRecyclerView
import com.example.artjohn.blackfin.model.*
import com.example.artjohn.blackfin.presenter.benefits.BenefitsMVP
import com.example.artjohn.blackfin.presenter.benefits.BenefitsPresenter
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_benefits.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity


class BenefitsActivity : BaseActivity(), BenefitsMVP.View
{


    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    val presenter = BenefitsPresenter(this, apiServer)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefits)
        title = "Benefits"

        presenter.processAdapter()

        benefitsRecyclerView.layoutManager = GridLayoutManager(this,2)
        benefitsNextButton.setOnClickListener {
                startActivity<SummaryActivity>()
        }

    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConfigureBenefits(event : ConfigureBenefits)
    {
        benefitsRecyclerView.adapter.notifyDataSetChanged()
    }

    /*event for progress bar when calling api*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBenefitsProgressbar(event : BenefitsProgressBar)
    {

        if(event.visible)
        {
            benefitsProgressbar.visibility = View.VISIBLE
        }
        else
        {
            benefitsProgressbar.visibility = View.GONE
        }

        if(event.buttonVisible)
        {
            benefitsNextButton.visibility = View.VISIBLE
        }

    }

    /*check benefits adapter if empty*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCheckRecyclerView(event : CheckRecyclerView)
    {
        try
        {
            benefitsRecyclerView.adapter.itemCount
        }catch (e : Exception)
        {
            benefitsProgressbar.visibility = View.VISIBLE
            presenter.processAdapter()
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
    override fun setAdapter(product : Product.List?, provider : Provider.Result?)
    {
        benefitsRecyclerView.adapter = BenefitsAdapter(this,product,provider)
    }
}
