package com.example.artjohn.blackfin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.artjohn.blackfin.R
import kotlinx.android.synthetic.main.occupation_guide_layout.view.*

class OccupationGuideAdapter(context: Context) : RecyclerView.Adapter<OccupationGuideAdapter.OccupationGuideHolder>() {

    //region - Variable
    var context : Context = context
    var array = arrayOf (
            R.string.guide_1,
            R.string.guide_2,
            R.string.guide_3,
            R.string.guide_4,
            R.string.guide_5
            )
    //endregion

    //region - Adapter Delegate
    override fun onBindViewHolder(holder: OccupationGuideHolder,
                                  position: Int) {
        holder.title.text = "Occupation Class " + (position.plus(1)).toString()
        holder.content.text = context.resources.getString(array[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): OccupationGuideAdapter.OccupationGuideHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.occupation_guide_layout,
                parent,
                false)
        return OccupationGuideHolder(layout)
    }

    override fun getItemCount(): Int {
        return array.size
    }
    //endregion

    //region - View Holder
    class OccupationGuideHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content : TextView = itemView.findViewById(R.id.contentText)
        var title : TextView = itemView.findViewById(R.id.titleText)
    }
    //endregion
}