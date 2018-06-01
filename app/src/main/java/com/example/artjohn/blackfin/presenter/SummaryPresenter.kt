package com.example.artjohn.blackfin.presenter

import com.example.artjohn.blackfin.model.Client
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.QouteRequest
import com.example.artjohn.blackfin.services.ApiServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SummaryPresenterClass(val view : SummaryView,
                            val server : ApiServices) : SummaryPresenter {



    //region - Variables
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    var frequency = arrayOf(
            "Annually",
            "Monthly",
            "Fortnightly")
    //endregion

    //region - Presenter Delegate
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

    override fun processFrequency() {
        view.setFrequencySpinner(frequency)
    }

    override fun processChangeFrequency(id: Int) {
        var index : Int = 0
        var frequency = 1

        when(id) {
            0 -> {
                frequency = 1
            }
            1 -> {
                frequency = 12
            }
            2 -> {
                frequency = 26
            }
        }

        ConfigureBenefits.array.forEach { data ->
            ConfigureBenefits.array[index].inputs.frequency = frequency
            index++
        }
        view.updatedFrequency()
    }
    //endregion
}

interface SummaryView {
    fun setAdapter(data : QouteRequest.Result, dataSorted : QouteRequest.Result)
    fun setFrequencySpinner(data : Array<String>)
    fun requestFailed()
    fun updatedFrequency()
}

interface SummaryPresenter {
    fun processRecyclerAdapter(data : Client)
    fun processFrequency()
    fun processChangeFrequency(id : Int)

}