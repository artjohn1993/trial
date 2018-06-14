package com.example.artjohn.blackfin.presenter

import com.example.artjohn.blackfin.model.Benefit
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.services.ApiServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductSettingPresenterClass(val view : ProductSettingView,
                                   val server : ApiServices) : ProductSettingPresenter{

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun processAdapter() {
        compositeDisposable.add(
                server.getProduct()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.setAdapter(result)
                        },{
                            error ->
                            println(error)
                        })
        )

    }
}

interface ProductSettingView {
    fun setAdapter(data : Product.List)
}
interface ProductSettingPresenter {
    fun processAdapter()
}