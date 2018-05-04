package com.example.artjohn.blackfin

import android.app.Dialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.artjohn.blackfin.dialog.HealthDialog
import com.example.artjohn.blackfin.event.CheckRecyclerView
import com.example.artjohn.blackfin.event.ConnectivityReceiver
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.alert
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.linearLayout

/**
 * Created by User on 02/05/2018.
 */
open class BaseActivity : AppCompatActivity(),ConnectivityReceiver.ConnectivityReceiverListener {
    private var dialog : Dialog? = null
    private  var snackBar : Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = Dialog(this)
        dialog?.setContentView(R.layout.layout_noconnection)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.primaryTransparency)
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    fun showMessage(isConnected: Boolean)
    {
        if(!isConnected)
        {

            val messageToUser = "You are offline now." //TODO

            snackBar = Snackbar.make(window.decorView.rootView, messageToUser, Snackbar.LENGTH_LONG) //Assume "rootLayout" as the root layout of every activity.
            snackBar?.duration = Snackbar.LENGTH_INDEFINITE
            snackBar?.show()

        }
        else
        {
            snackBar?.dismiss()
            EventBus.getDefault().post(CheckRecyclerView())
        }
    }

    override fun onResume() {
        super.onResume()

        ConnectivityReceiver.connectivityReceiverListener = this
    }
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }
}