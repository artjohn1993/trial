package com.example.artjohn.blackfin

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.GridLayout
import com.example.artjohn.blackfin.adapter.BenefitsAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.dialog.HealthDialog
import com.example.artjohn.blackfin.dialog.LifeDialog
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.Qoute
import com.example.artjohn.blackfin.model.SignIn
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_benefits.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.EventBus



class BenefitsActivity : AppCompatActivity() {

    private var disposable : Disposable? = null
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefits)
        title = "Benefits"
        benefitsRecyclerView.layoutManager = GridLayoutManager(this,2)
        benefitsRecyclerView.adapter = BenefitsAdapter(this)

        println(ClientInfo.array)

        benefitsNextButton.setOnClickListener {

        }


        println("============================BEFORE CALLING THE API===============================")
        var signin = SignIn("droid1-qatest@mail.com","firebrand",true)
        disposable = apiServer.login(signin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({result ->
                    println(result.data.user.id)
                    println(result.data.authorization.token)
                },{
                    error ->
                    print(error.toString())
                })
        println("============================AFTER CALLING THE API===============================")

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
        disposable?.dispose()

    }
}
