package com.example.artjohn.blackfin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.Product
import com.squareup.moshi.Moshi

class SelectedProductActivity : BaseActivity() {

    //region - Variables
    var benefitID : Int = 0
    var providerID : Int = 0
    var product : Product.List? = null
    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_product)
        title = "Select a Product"
        setData()
    }
    //endregion

    //region - Private method
    private fun setData() {
        benefitID = intent.getIntExtra("benefitID",0)
        providerID = intent.getIntExtra("providerID",0)
        product = jsonToObject(intent.getStringExtra("data"))
    }
    private fun jsonToObject(data: String) : Product.List {
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(Product.List::class.java)
        var json = jsonAdapter.fromJson(data)
        return json
    }
    private fun dataArray(data : Product.List?) : List<Product.Products> {
        var items = data?.data?.products
        var array: ArrayList<Product.Products> = arrayListOf()
        for (index in 0 until product?.data?.products!!.size) {
            if (providerID.plus(1) == items!![index].providerId && benefitID == items!![index].benefitId) {
                array.add(items!![index])
            }
        }
        return array
    }
    //endregion

}
