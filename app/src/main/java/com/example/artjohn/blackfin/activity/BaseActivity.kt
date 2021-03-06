package com.example.artjohn.blackfin.activity

import android.app.Dialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.event.CheckRecyclerView
import com.example.artjohn.blackfin.event.ConnectivityReceiver
import org.greenrobot.eventbus.EventBus

/**
 * Created by User on 02/05/2018.
 */
open class BaseActivity : AppCompatActivity(),
        ConnectivityReceiver.ConnectivityReceiverListener {
    //region - variables
    private var dialog : Dialog? = null
    private var snackBar : Snackbar? = null
    //endregion

    //region - Lifecycle methods
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        dialog = Dialog (this)
        dialog?.setContentView (R.layout.layout_noconnection)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
        registerReceiver(ConnectivityReceiver(),
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onResume () {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    //endregion

    //region - ConnectivityReceiver
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
    //endregion

    //region - Private methods
    private fun showMessage(isConnected: Boolean) {
        if(!isConnected) {
            val messageToUser = "You are offline now." //TODO
            snackBar = Snackbar.make(window.decorView.rootView,
                    messageToUser,
                    Snackbar.LENGTH_LONG)
            snackBar?.duration = Snackbar.LENGTH_INDEFINITE
            snackBar?.show()
        }
        else {
            snackBar?.dismiss()
            EventBus.getDefault().post(CheckRecyclerView())
        }
    }
    //endregion
}