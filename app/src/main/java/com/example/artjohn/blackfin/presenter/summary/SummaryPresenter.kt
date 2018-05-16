package com.example.artjohn.blackfin.presenter.summary

import com.example.artjohn.blackfin.model.Client
import com.example.artjohn.blackfin.model.QouteRequest
import com.example.artjohn.blackfin.services.ApiServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SummaryPresenter(val view : SummaryPresenterView,
                       val server : ApiServices) : SummaryPresenterInterface {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun processRecyclerAdapter(data : Client) {
        compositeDisposable.add(
                server.requestQoutes(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            var unSorted = result
                            result.data.data.result.providers.sortWith(Comparator { obj1,
                                                                                    obj2 ->
                                obj1.errorSummary.size.compareTo(obj2.errorSummary.size)
                            })
                            view.setAdapter(unSorted,result)
                        },{
                            error ->
                            print(error.message)
                            view.requestFailed()
                        })
        )
    }
}

interface SummaryPresenterView {
    fun setAdapter(data : QouteRequest.Result, dataSorted : QouteRequest.Result)
    fun requestFailed()
}

interface SummaryPresenterInterface {
    fun processRecyclerAdapter(data : Client)
}