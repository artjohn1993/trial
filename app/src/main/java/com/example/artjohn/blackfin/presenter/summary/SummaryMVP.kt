package com.example.artjohn.blackfin.presenter.summary

import com.example.artjohn.blackfin.model.Client
import com.example.artjohn.blackfin.model.QouteRequest

class SummaryMVP
{
    interface View
    {
        fun setAdapter(data : QouteRequest.Result, dataSorted : QouteRequest.Result)
        fun requestFailed()
    }
    interface Presenter
    {
        fun processRecyclerAdapter(data : Client)
    }

}