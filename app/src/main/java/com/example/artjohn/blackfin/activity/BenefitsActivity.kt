package com.example.artjohn.blackfin.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.BenefitsAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.event.BenefitsProgressBar
import com.example.artjohn.blackfin.event.CheckRecyclerView
import com.example.artjohn.blackfin.model.*
import com.example.artjohn.blackfin.presenter.BenefitsPresenterClass
import com.example.artjohn.blackfin.presenter.BenefitsView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_benefits.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity


class BenefitsActivity : BaseActivity(),
        BenefitsView {

    //region - Variables
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    val presenter = BenefitsPresenterClass(this, apiServer)
    var id : Int = 0
    //endregion

    //region - Lifecycler methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefits)
        asignId()
        title = "Benefits"
        presenter.processAdapter()
        benefitsRecyclerView.layoutManager = GridLayoutManager(this,
                2)
        benefitsNextButton.setOnClickListener {
                startActivity<PeopleActivity>()
        }

    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onResume() {
        super.onResume()
        asignId()
    }
    public override fun onPause() {
        super.onPause()
        compositeDisposable.clear()

    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    //endregion

    //region - Eventbus
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onConfigureBenefits(event : ConfigureBenefits) {
        benefitsRecyclerView.adapter.notifyDataSetChanged()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBenefitsProgressbar(event : BenefitsProgressBar) {
        if(event.visible) {
            benefitsProgressbar.visibility = View.VISIBLE
        }
        else {
            benefitsProgressbar.visibility = View.GONE
        }

        if(event.buttonVisible) {
            benefitsNextButton.visibility = View.VISIBLE
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCheckRecyclerView(event : CheckRecyclerView) {
        try {
            benefitsRecyclerView.adapter.itemCount
        }catch (e : Exception) {
            benefitsProgressbar.visibility = View.VISIBLE
            presenter.processAdapter()
        }
    }
    //endregion

    //region - Presenter Delegates
    override fun setAdapter(product : Product.List?, provider : Provider.Result?) {
        benefitsRecyclerView.adapter = BenefitsAdapter(this,
                product,
                provider,
                id)
    }
    //endregion

    //region - Private methods
    private fun asignId() {
        val extras = intent.getStringExtra("clientId")
        if (extras != null) {
            id = extras.toInt()
        }
    }
    //endregion
}
