package com.example.artjohn.blackfin.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.PeopleAdapter
import com.example.artjohn.blackfin.adapter.QouteSettingsAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.dialog.LoadingDialog
import com.example.artjohn.blackfin.event.PremiumRange
import com.example.artjohn.blackfin.event.ViewProduct
import com.example.artjohn.blackfin.model.Benefit
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider
import com.example.artjohn.blackfin.model.QouteSettings
import com.example.artjohn.blackfin.presenter.*
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_qoute_setting.*
import kotlinx.android.synthetic.main.activity_summary.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity


class QouteSettingActivity : BaseActivity(),QouteSettingsView {

    //region - Variables
    var loading = LoadingDialog(this)
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    val presenter : QouteSettingsPresenter = QouteSettingsPresenterClass(this, apiServer)
    var userID = "ba3e6661-2f25-4bb9-b08d-6509eb0ad524"
    var qouteSettings : QouteSettings.Result? = null
    var productList : Product.List? = null
    var moshi = Moshi.Builder().build()
    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qoute_setting)
        title = "Qoute Settings"
        loading.show()
        presenter.processAdapter(QouteSettings.Body(userID, 0))
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
        var selectedProvider = qouteSettings?.data?.providers?.get(event.id)
        var intent = Intent(baseContext, ProductSettingsActivity::class.java)
        intent.putExtra("data", qouteSettingsJson(selectedProvider!!))
        intent.putExtra("product", productJson(this!!.productList!!))
        startActivity(intent)
    }
    //endregion

    //region - Presenter Delegates
    override fun setAdapter(data : QouteSettings.Result, data2 : Product.List) {
        qouteSettings = data
        productList = data2
        qouteSettingsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        qouteSettingsRecyclerView.adapter = QouteSettingsAdapter(qouteSettings!!, productList!!)
        loading.hide()
    }
    //endregion

    //region - Private method
    private fun qouteSettingsJson(data : QouteSettings.Providers) : String {
        val jsonAdapter = moshi.adapter(QouteSettings.Providers::class.java)
        var json : String = jsonAdapter.toJson(data)
        return json
    }
    private fun productJson(data : Product.List) : String {
        val jsonAdapter = moshi.adapter(Product.List::class.java)
        var json : String = jsonAdapter.toJson(data)
        return json
    }
    //endregion
}
