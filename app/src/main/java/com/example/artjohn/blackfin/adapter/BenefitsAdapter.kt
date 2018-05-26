package com.example.artjohn.blackfin.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.dialog.HealthDialog
import com.example.artjohn.blackfin.dialog.LifeDialog
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.Product
import com.example.artjohn.blackfin.model.Provider
import kotlinx.android.synthetic.main.benefits_layout.view.*
import org.jetbrains.anko.textColor

class BenefitsAdapter(activity: Activity,
                      product : Product.List?,
                      provider : Provider.Result?,
                      clientId : Int) : RecyclerView.Adapter<BenefitsAdapter.BenefitsHolder>() {
    //region - Variables
    var activity = activity
    var product = product
    var provider = provider
    var clientId : Int = clientId
    var titleArray  = arrayOf (
            "Health Cover",
            "Life Cover",
            "Family Protection",
            "Trauma",
            "Total and Permanent Disability",
            "Income Protection",
            "Mortgage Repayment Cover",
            "Redundancy Cover",
            "Waiver of Premium"
    )
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
    //endregion

    //region - Adapter Delegate
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): BenefitsHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.benefits_layout,
                parent,
                false)
        return BenefitsHolder(layout)
    }

    override fun getItemCount(): Int {
        return titleArray.size
    }

    override fun onBindViewHolder(holder: BenefitsHolder,
                                  position: Int) {
        holder.icon.setImageResource(imageArray[position])
        holder.title.text = titleArray[position]
        var exist : Boolean = false

        for (x in 0 until ConfigureBenefits.array.size) {
            if (ConfigureBenefits.array[x].clientId == clientId && ConfigureBenefits.array[x].inputs.benefitProductList[0].benefitId == position.plus(1)) {
                holder.title.setTextColor(Color.parseColor("#3cbdd0"))
                holder.icon.setColorFilter(Color.parseColor("#3cbdd0"))
                holder.wrapper.setBackgroundResource(R.drawable.summary_color_provider)
                break
            }
        }
        if (!exist) {
            holder.title.setTextColor(Color.parseColor("#010026"))
            holder.icon.setColorFilter(Color.parseColor("#010026"))
        }


        holder.itemView.setOnClickListener {
                if (position == 0) {
                        HealthDialog().show(activity, product, provider, clientId)
                }
                else if(position == 1) {
                    LifeDialog().show(activity, product, provider, clientId)
                }
        }
    }
    //endregion

    //region - View Holder
    class BenefitsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon : ImageView = itemView.findViewById(R.id.iconImage)
        var title  : TextView = itemView.findViewById(R.id.benefitsTitleText)
        var wrapper : android.support.constraint.ConstraintLayout = itemView.findViewById(R.id.benefitsWrapper)
    }
    //endregion
}