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
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.presenter.ProductSettingPresenterClass
import com.example.artjohn.blackfin.presenter.ProductSettingView
import com.example.artjohn.blackfin.presenter.QouteSettingsPresenter
import com.example.artjohn.blackfin.presenter.QouteSettingsPresenterClass
import kotlinx.android.synthetic.main.activity_product_settings.*
import kotlinx.android.synthetic.main.activity_qoute_setting.*
import kotlinx.android.synthetic.main.layout_product_settings.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ProductSettingsActivity : BaseActivity(), ProductSettingView {

    //region - Variables
    var product : Product.List? = null
    var loading = LoadingDialog(this)
    var id : Int = 0
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
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    val presenter : ProductSettingPresenterClass = ProductSettingPresenterClass(this, apiServer)
    //endregion

    //region - Life cycle method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_settings)
        title = "Product Settings"
        setID()
        loading.show()
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

    //region - Private method
    private fun setID() {
        id = intent.getIntExtra("id", 0)
        setImage()
    }
    private fun setImage() {
        providerLogo.setImageResource(imageArray[id])
    }

    private fun setBenefitResult(data : Product.List) : List<Product.Products> {
        var array : ArrayList<Product.Products> = arrayListOf()
        var product = data.data.products
        var exist : ArrayList<Int> = arrayListOf()
       for (index in 0 until product.size) {
           if (id.plus(1) == product[index].providerId && !exist.contains(product[index].benefitId)) {
               exist.add(product[index].benefitId)
               array.add(product[index])
           }
       }
       return array
    }
    //endregion

    //region - Presenter delegates
    override fun setAdapter(data : Product.List) {
        product = data
        productRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        productRecyclerView.adapter = ProductSettingsAdapter(setBenefitResult(product!!))
        loading.hide()
    }
    //endregion

    //region - EventBus method
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSelectActiveProducr(event : SelectActiveProduct) {
        var intent = Intent(baseContext, SelectedProductActivity::class.java)


    }
    //endregion
}
