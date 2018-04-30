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
import com.example.artjohn.blackfin.model.*
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


class BenefitsActivity : AppCompatActivity() {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    var product : Product.List? = null
    var provider : Provider.Result? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefits)
        title = "Benefits"
        getProduct()

        benefitsRecyclerView.layoutManager = GridLayoutManager(this,2)



        benefitsNextButton.setOnClickListener {
                startActivity<SummaryActivity>()
        }

    }


    fun getProduct()
    {

        compositeDisposable?.add(
                apiServer.getProduct()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({result ->
                            println(result)
                            getProvider(result)
                        },{
                            error ->
                            print(error.toString())
                        })
        )

    }

    fun getProvider(value : Product.List)
    {

        compositeDisposable?.add(
                apiServer.getProvider()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({result ->
                            println(result)
                            benefitsProgressbar.visibility = View.GONE
                            benefitsNextButton.visibility = View.VISIBLE
                            benefitsRecyclerView.adapter = BenefitsAdapter(this,value,result)
                        },{
                            error ->
                            benefitsProgressbar.visibility = View.GONE
                            print(error.toString())
                        })
        )

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConfigureBenefits(event : ConfigureBenefits)
    {
        benefitsRecyclerView.adapter.notifyDataSetChanged()
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
