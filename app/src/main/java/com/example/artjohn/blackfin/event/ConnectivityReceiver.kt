package com.example.artjohn.blackfin.event

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

/**
 * Created by User on 02/05/2018.
 */
class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (connectivityReceiverListener != null)
        {
            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnected(context))
        }
    }

    private fun isConnected(context: Context) : Boolean
    {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
}