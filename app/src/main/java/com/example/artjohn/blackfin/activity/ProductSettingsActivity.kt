package com.example.artjohn.blackfin.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.ProductSettingsAdapter
import com.example.artjohn.blackfin.adapter.QouteSettingsAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.dialog.LoadingDialog
import com.example.artjohn.blackfin.event.SelectActiveProduct
import com.example.artjohn.blackfin.event.ViewProduct
import com.example.artjohn.blackfin.model.Benefit
import com.example.artjohn.blackfin.model.ConfigureBenefits.Companion.id
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.QouteSettings
import com.example.artjohn.blackfin.presenter.ProductSettingPresenterClass
import com.example.artjohn.blackfin.presenter.ProductSettingView
import com.example.artjohn.blackfin.presenter.QouteSettingsPresenter
import com.example.artjohn.blackfin.presenter.QouteSettingsPresenterClass
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_product_settings.*
import kotlinx.android.synthetic.main.activity_qoute_setting.*
import kotlinx.android.synthetic.main.layout_product_settings.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ProductSettingsActivity : BaseActivity() {

    //region - Variables
    var product : String = ""
    var loading = LoadingDialog(this)
    var data : QouteSettings.Providers? = null
    var imageArray : Array<Int> = arrayOf (
            R.drawable.ic_accuro,
            R.drawable.ic_aia,
            R.drawable.ic_amp,
            R.drawable.ic_amprpp,
            R.drawable.ic_asteron,
            R.drawable.ic_fidelity,
            R.drawable.ic_nib,
            R.drawable.ic_onepath,
            R.drawable.ic_partnerslife,
            R.drawable.ic_southerncross,
            R.drawable.ic_sovereign,
            R.drawable.ic_fidelity
    )
    var moshi = Moshi.Builder().build()
    //endregion

    //region - Life cycle method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_settings)
        title = "Product Settings"
        bind()
    }
    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        productRecyclerView.adapter.notifyDataSetChanged()
    }
    //endregion

    //region - Private method
    private fun bind() {
        getIntentData()
        setImage()
        setAdapter()
    }
    private fun getIntentData() {
        val jsonAdapter = moshi.adapter(QouteSettings.Providers::class.java)
        data = jsonAdapter.fromJson(intent.getStringExtra("data"))
        product = intent.getStringExtra("product")
    }
    private fun setImage() {
        providerLogo.setImageResource(imageArray[data?.providerId!! - 1])
    }
    private fun setAdapter() {
        productRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        productRecyclerView.adapter = ProductSettingsAdapter(this.data?.benefits!!)
        loading.hide()
    }
    //endregion

    //region - EventBus method
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSelectActiveProduct(event : SelectActiveProduct) {
        var intent = Intent(baseContext, SelectedProductActivity::class.java)
        intent.putExtra("benefitID", event.id)
        intent.putExtra("providerID", data?.providerId)
        intent.putExtra("product", product)
        startActivity(intent)
    }
    //endregion
}
