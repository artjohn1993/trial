package com.example.artjohn.blackfin.presenter


import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider
import com.example.artjohn.blackfin.model.QouteSettings
import com.example.artjohn.blackfin.services.ApiServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class QouteSettingsPresenterClass(val view : QouteSettingsView,
                                  val server : ApiServices) : QouteSettingsPresenter{


    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun processAdapter(data : QouteSettings.Body) {
        compositeDisposable.add(
                server.requestQouteSettings(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            getProduct(result)
                        },{
                            error ->
                            println(error)
                        })
        )
    }
    override fun getProduct(data : QouteSettings.Result) {
        compositeDisposable.add(
                server.getProduct()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.setAdapter(data, result)
                        },{
                            error ->
                            println(error)
                        })
        )
    }
}

interface QouteSettingsView {
    fun setAdapter(data : QouteSettings.Result, data2 : Product.List)
}

interface QouteSettingsPresenter {
    fun processAdapter(data : QouteSettings.Body)
    fun getProduct(data : QouteSettings.Result)
}