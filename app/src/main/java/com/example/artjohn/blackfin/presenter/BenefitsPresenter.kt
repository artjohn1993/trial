package com.example.artjohn.blackfin.presenter

import com.example.artjohn.blackfin.event.BenefitsProgressBar
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider
import com.example.artjohn.blackfin.services.ApiServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class BenefitsPresenterClass(val view : BenefitsView,
                             val server : ApiServices) : BenefitsPresenter {
    //region - Variables
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    //endregion

    //region - BenefitsPresenter Delegate
    override fun processAdapter() {
       getProduct()
    }
    override fun getProduct() {
        compositeDisposable?.add(
                server.getProduct()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({result ->
                            getProvider(result)
                        },{
                            error ->
                            EventBus.getDefault().post(BenefitsProgressBar(false,false))
                        })
        )
    }

    override fun getProvider(value : Product.List) {
        compositeDisposable?.add(
                server.getProvider()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({result ->
                            view.setAdapter(value,result)
                            EventBus.getDefault().post(BenefitsProgressBar(false,true))
                        },{
                            error ->
                            EventBus.getDefault().post(BenefitsProgressBar(false,false))
                        })
        )
    }
    //endregion
}

interface BenefitsView {
    fun setAdapter(product : Product.List?, provider : Provider.Result?)
}

interface BenefitsPresenter {
    fun processAdapter()
    fun getProduct()
    fun getProvider(value : Product.List)
}