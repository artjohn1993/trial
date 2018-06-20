package com.example.artjohn.blackfin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.event.ProviderCheckEvent
import com.example.artjohn.blackfin.event.ViewProduct
import com.example.artjohn.blackfin.model.Benefit
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider
import com.example.artjohn.blackfin.model.QouteSettings
import org.greenrobot.eventbus.EventBus

class QouteSettingsAdapter(val data : QouteSettings.Result, val data2 : Product.List) : RecyclerView.Adapter<QouteSettingsAdapter.QouteSettingsViewHolder>() {

    //region - Variables
    var imageArray : Array<Int> = arrayOf (
            R.drawable.ic_accuro,
            R.drawable.ic_aia,
            R.drawable.ic_amp,
            R.drawable.ic_amprpp,
            R.drawable.ic_asteron,
            R.drawable.ic_fidelity,
            R.drawable.ic_nib,
            R.drawable.ic_onepath,
            R.drawable.ic_partnerslife,
            R.drawable.ic_southerncross,
            R.drawable.ic_sovereign,
            R.drawable.ic_fidelity
    )
    //endregion

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QouteSettingsAdapter.QouteSettingsViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_qoute_settings,parent,false)
        return QouteSettingsViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.data.providers.size
    }

    override fun onBindViewHolder(holder: QouteSettingsAdapter.QouteSettingsViewHolder, position: Int) {
        holder.image.setImageResource(imageArray[data.data.providers[position].providerId - 1])
        holder.switch.isChecked = data.data.providers[position].providerStatus

        holder.switch.setOnCheckedChangeListener { buttonView, isChecked ->
            EventBus.getDefault().post(ProviderCheckEvent(data.data.providers[position].providerId,
                    isChecked))
        }

        holder.itemView.setOnClickListener {
            EventBus.getDefault().post(ViewProduct(position))
        }
    }

    class QouteSettingsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.icon)
        var switch = itemView.findViewById<Switch>(R.id.switchButton)
    }
}