package com.example.artjohn.blackfin.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.event.ErrorEvent
import com.example.artjohn.blackfin.event.ProductPremium
import com.example.artjohn.blackfin.event.SummaryNotAvailable
import com.example.artjohn.blackfin.model.*
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.backgroundColor

class SummaryAdapterTwo(data : QouteRequest.Result) : RecyclerView.Adapter<SummaryAdapterTwo.SummaryViewHolder>() {
    //region - Variables
    var qoute = data
    var imageArray : Array<Int> = arrayOf(
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
    var colorArray : Array<String> = arrayOf(
            "#00aeef",
            "#d21e47",
            "#1f2b5b",
            "#1c2a5b",
            "#1d98db",
            "#970000",
            "#009261",
            "#739500",
            "#c3cf21",
            "#3193cc",
            "#1e384b",
            "#009261"
    )
    //endregion

    //region - RecyclerView Lifecycle
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_summary_two,parent,false)
        return SummaryViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return qoute.data.data.result.providers.size
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        if (qoute.data.data.result.providers[position].containsError) {
            EventBus.getDefault().post(SummaryNotAvailable(true))
            holder.title.text = qoute.data.data.result.providers[position].providerName
            holder.price.text = "$" + qoute.data.data.result.providers[position].totalPremium.toString()
            var id = qoute.data.data.result.providers[position].providerId
            holder.logo.setImageResource(imageArray[id-1])
            holder.color.backgroundColor = Color.parseColor(colorArray[id-1])

            if(qoute.data.data.result.providers[position].errorSummary.isNotEmpty()) {
                holder.container.alpha = .5f
            }
        }
        else {
            holder.container.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            if(qoute.data.data.result.providers[position].errorSummary.isNotEmpty())
            {
                var  message = qoute.data.data.result.providers[position].errorSummary[0].errorMessage
                EventBus.getDefault().post(ErrorEvent(message))
            }
            else {
                var clientInfoJson = Gson().toJson(ClientInfo.array)
                var qouteJson = Gson().toJson(qoute)
                EventBus.getDefault().post(ProductPremium(clientInfoJson, qouteJson, position))
            }
        }
    }
    //endregion

    //region - Child class
    class SummaryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.summaryTitle)
        var price = itemView.findViewById<TextView>(R.id.summaryPrice)
        var logo = itemView.findViewById<ImageView>(R.id.summaryLogo)
        var container = itemView.findViewById<android.support.constraint.ConstraintLayout>(R.id.resultContainer)
        var color = itemView.findViewById<View>(R.id.colorProviderView)
    }
    //endregion
}