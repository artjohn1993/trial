package com.example.artjohn.blackfin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.event.SelectProduct
import com.example.artjohn.blackfin.model.Product
import org.greenrobot.eventbus.EventBus

class SelectedProductAdapter(val data: List<Product.Products>) : RecyclerView.Adapter<SelectedProductAdapter.SelectedProductViewHolder>() {
    var imageArray : Array<Int> = arrayOf (
            R.drawable.ic_01_health_benefits,
            R.drawable.ic_02_life_cover,
            R.drawable.ic_03_family_protection,
            R.drawable.ic_04_trauma,
            R.drawable.ic_05_disability,
            R.drawable.ic_06_income_protection,
            R.drawable.ic_07_mortgage,
            R.drawable.ic_08_redundancy,
            R.drawable.ic_09_waiver_of_premium
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedProductAdapter.SelectedProductViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_selected_product,parent,false)
        return SelectedProductViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(holder: SelectedProductAdapter.SelectedProductViewHolder, position: Int) {
        holder.image.setImageResource(imageArray[data[position].benefitId - 1])
        holder.title.text = data[position].productName

        holder.itemView.setOnClickListener {
            EventBus.getDefault().post(SelectProduct(data[position].productGroupId, data[position].productName))
        }
    }

    class SelectedProductViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.findViewById<ImageView>(R.id.benefitIcon)
        var title = itemView.findViewById<TextView>(R.id.benefitTitleText)
    }
}