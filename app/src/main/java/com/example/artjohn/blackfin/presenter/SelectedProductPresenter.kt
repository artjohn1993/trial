package com.example.artjohn.blackfin.presenter

import android.view.View
import com.example.artjohn.blackfin.model.UpdateQouteSetting
import com.example.artjohn.blackfin.services.ApiServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SelectedProductPresenterClass(val view : SelectedProductView,
                                    val server : ApiServices) : SelectedProductPresenter {

    //region - Variables
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    //endregion

    override fun updateQouteSetting(data : UpdateQouteSetting.Body) {
        compositeDisposable.add(
                server.updateQouteSettings(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.updatedQouteSetting(result)
                        },{ error ->
                            println(error)
                        })
        )
    }

}

interface SelectedProductView {
    fun updatedQouteSetting(data : UpdateQouteSetting.Result)
}
interface SelectedProductPresenter {
    fun updateQouteSetting(data : UpdateQouteSetting.Body)
}