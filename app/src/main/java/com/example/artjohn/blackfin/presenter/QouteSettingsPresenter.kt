package com.example.artjohn.blackfin.presenter


import com.example.artjohn.blackfin.model.Provider
import com.example.artjohn.blackfin.services.ApiServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class QouteSettingsPresenterClass(val view : QouteSettingsView,
                                  val server : ApiServices) : QouteSettingsPresenter{
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun processAdapter() {
        compositeDisposable.add(
                server.getProvider()
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
interface QouteSettingsView {
    fun setAdapter(data : Provider.Result)
}

interface QouteSettingsPresenter {
    fun processAdapter()
}