package com.example.artjohn.blackfin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.QouteRequest
import com.example.artjohn.blackfin.event.TotalPremium
import org.greenrobot.eventbus.EventBus

/**
 * Created by User on 30/04/2018.
 */
class PremiumAdapter(qoute : QouteRequest.Result,
                     index : Int,
                     var clientID : Int) : RecyclerView.Adapter<PremiumAdapter.PremiumViewHolder>() {

    //region - Variables
    val qoute = qoute
    var  index = index
    var premium = qoute.data.data.result.providers[index].clientBreakdown[clientID].productPremiums
    //endregion

    //region - Adapter Delegate
    override fun onBindViewHolder(holder: PremiumViewHolder,
                                  position: Int) {

        if (premium[position].productName.isNullOrEmpty()) {
            holder.titleBreakdown.text = premium[position].benefitName
            holder.priceBreakdown.text = ""
            holder.detailsBreakdown.text = "Benefit " + premium[position].benefitName + " is not Available."
        }
        else {
            holder.titleBreakdown.text = premium[position].productName
            holder.priceBreakdown.text = "$" + premium[position].premium.toString()
            holder.detailsBreakdown.text = premium[position].benefitName
            holder.error.visibility = View.GONE
            EventBus.getDefault().post(TotalPremium(premium[position].premium))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PremiumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.layout_breakdown,
                parent,
                false)
        return PremiumViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return premium.size
    }
    //endregion

    //region - View Holder
    class PremiumViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var titleBreakdown = itemview.findViewById<TextView>(R.id.breakdownTitle)
        var detailsBreakdown = itemview.findViewById<TextView>(R.id.breakdownDetails)
        var priceBreakdown = itemview.findViewById<TextView>(R.id.breakdownPrice)
        var error = itemview.findViewById<ImageView>(R.id.errorImage)
    }
    //endregion
}