package com.example.artjohn.blackfin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.artjohn.blackfin.R
import kotlinx.android.synthetic.main.occupation_guide_layout.view.*

class OccupationGuideAdapter(context: Context) : RecyclerView.Adapter<OccupationGuideAdapter.OccupationGuideHolder>()
{

    var context : Context = context

    var array = arrayOf(
            "samle",
            "saasdasdad",
            "asdasdasdasd"
            )

    override fun onBindViewHolder(holder: OccupationGuideHolder, position: Int) {
        holder.title.text = "Occupation Class " + position.toString()
        holder.content.text = "sample text"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OccupationGuideAdapter.OccupationGuideHolder {

        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.occupation_guide_layout,parent,false)

        return OccupationGuideHolder(layout)
    }

    override fun getItemCount(): Int {
        return 10
    }



    class OccupationGuideHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var content : TextView = itemView.findViewById(R.id.contentText)
        var title : TextView = itemView.findViewById(R.id.titleText)
    }

}