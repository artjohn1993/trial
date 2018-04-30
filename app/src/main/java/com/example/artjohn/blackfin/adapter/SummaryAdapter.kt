package com.example.artjohn.blackfin.adapter

import android.graphics.Color
import android.support.constraint.Constraints
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.PremiumRange
import com.example.artjohn.blackfin.model.QouteRequest
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.backgroundColor
import java.util.zip.Inflater

class SummaryAdapter(data : QouteRequest.Result) : RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder>() {
    var qoute = data
    var max : Double = 0.0
    var min : Double = 0.0
    var totalProvider : Int = 0
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_summary,parent,false)

        return SummaryViewHolder(layout)
    }

    override fun getItemCount(): Int {
        println(qoute.data.data.result.providers.size)
        return qoute.data.data.result.providers.size
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {


            if (qoute.data.data.result.providers[position].errorSummary.isEmpty()) {
                holder.title.text = qoute.data.data.result.providers[position].providerName
                holder.price.text = "$" + qoute.data.data.result.providers[position].totalPremium.toString()
                var id = qoute.data.data.result.providers[position].providerId
                holder.logo.setImageResource(imageArray[id-1])
                holder.color.backgroundColor = Color.parseColor(colorArray[id-1])
                calculateRange(qoute.data.data.result.providers[position].totalPremium)
            }
             else
            {
                holder.container.visibility = View.GONE
            }
    }


    class SummaryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        var title = itemView.findViewById<TextView>(R.id.summaryTitle)
        var price = itemView.findViewById<TextView>(R.id.summaryPrice)
        var logo = itemView.findViewById<ImageView>(R.id.summaryLogo)
        var container = itemView.findViewById<android.support.constraint.ConstraintLayout>(R.id.resultContainer)
        var color = itemView.findViewById<View>(R.id.colorProviderView)
    }
    fun calculateRange(number : Double)
    {
        if (min == 0.0)
        {
            min = number
        }
        else if(min>number)
        {
            min = number
        }
        else
        {
            if(max<number)
            {
                max = number
            }
        }
        totalProvider = totalProvider.plus(1)
        EventBus.getDefault().post(PremiumRange(min,max,totalProvider))
    }
}