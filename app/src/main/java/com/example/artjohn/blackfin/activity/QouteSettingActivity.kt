package com.example.artjohn.blackfin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.PeopleAdapter
import com.example.artjohn.blackfin.adapter.QouteSettingsAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.event.PremiumRange
import com.example.artjohn.blackfin.event.ViewProduct
import com.example.artjohn.blackfin.model.Benefit
import com.example.artjohn.blackfin.model.Provider
import com.example.artjohn.blackfin.presenter.*
import kotlinx.android.synthetic.main.activity_qoute_setting.*
import kotlinx.android.synthetic.main.activity_summary.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity


class QouteSettingActivity : BaseActivity(),QouteSettingsView {

    //region - Variables
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    val presenter : QouteSettingsPresenter = QouteSettingsPresenterClass(this, apiServer)
    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qoute_setting)
        title = "Qoute Settings"
        presenter.processAdapter()
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

    //region - EventBus method
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onViewProduct(event : ViewProduct) {
        startActivity<ProductSettingsActivity>()
    }
    //endregion
    //region - Presenter Delegates
    override fun setAdapter(data : Provider.Result) {
        qouteSettingsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        qouteSettingsRecyclerView.adapter = QouteSettingsAdapter(data)
    }
    //endregion
}
