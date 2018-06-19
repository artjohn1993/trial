package com.example.artjohn.blackfin.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.event.SelectProduct
import com.example.artjohn.blackfin.adapter.SelectedProductAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.dialog.LoadingDialog
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.UpdateQouteSetting
import com.example.artjohn.blackfin.presenter.SelectedProductPresenterClass
import com.example.artjohn.blackfin.presenter.SelectedProductView
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_selected_product.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SelectedProductActivity : BaseActivity(), SelectedProductView {

    //region - Variables
    var benefitID : Int = 0
    var providerID : Int = 0
    var product : Product.List? = null
    var moshi = Moshi.Builder().build()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }
    val presenter : SelectedProductPresenterClass = SelectedProductPresenterClass(this, apiServer)
    var userID = "ba3e6661-2f25-4bb9-b08d-6509eb0ad524"
    var loading = LoadingDialog(this)
    var data = ProductSettingsActivity().data
    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_product)
        title = "Select a Product"
        setData()
        setAdapter()
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
    fun onSelectProduct(event : SelectProduct) {
        var  array : ArrayList<UpdateQouteSetting.Records> = arrayListOf()
        array.add(UpdateQouteSetting.Records(providerID,
                benefitID,
                event.groupID))
        loading.show()
        presenter.updateQouteSetting(UpdateQouteSetting.Body(
                userID,
                0,
                array ))
    }
    //endregion

    //region - Private method
    private fun updateProduct(name : String, id : Int) {
        for (index in 0 until data?.benefits?.size!!) {
            if (benefitID == data?.benefits!![index].benefitId) {
                data?.benefits!![index].product.productName = name
                data?.benefits!![index].product.defaultProductGroupId = id
                ProductSettingsActivity().data = data
            }
        }
    }
    private fun setData() {
        benefitID = intent.getIntExtra("benefitID",0)
        providerID = intent.getIntExtra("providerID",0)
        product = jsonToObject(intent.getStringExtra("product"))
    }
    private fun jsonToObject(data: String) : Product.List {
        var jsonAdapter = moshi.adapter(Product.List::class.java)
        var json = jsonAdapter.fromJson(data)
        return json
    }
    private fun setAdapter() {
        var filtered : List<Product.Products> = product?.data?.products?.filter { data ->
            data.benefitId == benefitID && data.providerId == providerID
        }!!
        selectedRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        selectedRecyclerView.adapter = SelectedProductAdapter(filtered)
    }

    //endregion

    //region - EventBus
    override fun updatedQouteSetting(data: UpdateQouteSetting.Result) {
        loading.hide()
        Snackbar.make(window.decorView.rootView,
                data.data.message,
                Snackbar.LENGTH_LONG).show()
    }
    //endregion

}
