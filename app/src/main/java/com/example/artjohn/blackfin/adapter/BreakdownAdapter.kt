package com.example.artjohn.blackfin.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.QouteRequest
import kotlinx.android.synthetic.main.layout_main_breakdown.*

class BreakdownAdapter(var qoute : QouteRequest.Result,
                       var index : Int,
                       var context :  Context) : RecyclerView.Adapter<BreakdownAdapter.BreakdownViewHolder>() {
    //region - Variables
    var data = ClientInfo.array
    //endregion

    //region - Adapter Delegates
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreakdownAdapter.BreakdownViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var layout = inflater.inflate(R.layout.layout_main_breakdown, parent, false)
        return BreakdownViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BreakdownAdapter.BreakdownViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.image.setImageResource(userImage(position))
        holder.details.text = details(position)
        holder.benefitsRecycler.layoutManager = LinearLayoutManager(context,
                LinearLayout.VERTICAL,
                false)
        holder.benefitsRecycler.adapter = PremiumAdapter(qoute,
                index,
                position)

    }
    //endregion

    //region - ViewHolder
    class BreakdownViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var image = itemview.findViewById<ImageView>(R.id.profile)
        var name = itemview.findViewById<TextView>(R.id.nameText)
        var details = itemview.findViewById<TextView>(R.id.detailsText)
        var benefitsRecycler = itemview.findViewById<RecyclerView>(R.id.producPremiumRecyclerView)
    }
    //endregion

    //region - Private methods
    private fun userImage(position : Int) : Int {

        if (data[position].gender == "M") {
            if (data[position].age.toInt() > 17) {
                return R.drawable.icon_male
            }
            else {
                return R.drawable.icon_boy
            }
        }
        else {
            if (data[position].age.toInt() > 17) {
                return R.drawable.icon_female
            }
            else {
                return R.drawable.icon_girl
            }
        }
    }

    private fun details(position : Int) : String {
        var details : String = ""
        details = data[position].age
        details += ", " + convertGender(data[position].gender)
        details += ", " + convertSmoker(data[position].isSmoker)
        details += ", " + data[position].employedStatus

        return  details
    }
    private fun convertGender(gender : String) : String {
        if (gender == "M") {
            return "Male"
        }
        else {
            return "Female"
        }
    }
    private  fun convertSmoker(smoker : Boolean) : String {
        if (smoker) {
            return "Smoker"
        }
        else {
            return "Non-Smoker"
        }
    }
    //endregion
}