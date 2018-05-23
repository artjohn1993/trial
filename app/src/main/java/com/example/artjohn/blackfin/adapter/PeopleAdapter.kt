package com.example.artjohn.blackfin.adapter

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.ClientsInformation
import com.example.artjohn.blackfin.model.Inputs
import kotlinx.android.synthetic.main.layout_people.view.*

class PeopleAdapter(var info : ArrayList<ClientsInformation>, var config : ArrayList<Inputs>) : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    //region - Variables
    var age : Int  = 0
    var gender : String = ""
    var smoker : String = ""
    var userClass : String = ""
    var userOccupation : String = ""
    var benefits : String = ""
    //endregion

    //region - Adapter Delegate
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PeopleAdapter.PeopleViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var layout = inflater.inflate(R.layout.layout_people,
                parent,
                false)
        return PeopleViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return info.size
    }

    override fun onBindViewHolder(holder: PeopleAdapter.PeopleViewHolder,
                                  position: Int) {
        setDetails(position)
        setBenefits()
        holder.name.text = info[position].name
        holder.details.text = "$age, $gender, $smoker, $userClass"
        holder.benefits.text = benefits
        holder.profile.setImageResource(setProfilePic(info[position].age.toInt(), info[position].gender))

    }
    //endregion

    //region - View Holder
    class PeopleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var profile : ImageView = itemView.findViewById(R.id.profilePic)
        var name : TextView = itemView.findViewById(R.id.nameText)
        var details : TextView = itemView.findViewById(R.id.detailsText)
        var benefits : TextView = itemView.findViewById(R.id.benefitsValueText)
    }
    //endregion

    //region Private method
    private fun smoker(data : Boolean) : String {
        var smoker : String = ""

        if (data) {
            smoker = "Smoker"
        }
        else {
            smoker = "Non-Smoker"
        }

        return smoker
    }
    private fun setDetails(position : Int) {
        age = info[position].age.toInt()
        gender = info[position].gender
        smoker = smoker(info[position].isSmoker)
        userClass = info[position].employedStatus
    }
    private fun setBenefits() {
        for (index in 0 until config.size) {
            if (index == 0) {
                benefits = config[index].inputs.benefitsType
            }
            else {
                benefits = benefits + ", " + config[index].inputs.benefitsType
            }
        }
    }
    private fun setProfilePic(age : Int, gender : String) : Int {
        var image : Int = R.drawable.icon_boy
        if (age < 18) {
            if (gender == "M") {
                image = R.drawable.icon_boy
            }
            else {
                image = R.drawable.icon_girl
            }
        }
        else {
            if (gender == "M") {
                image = R.drawable.icon_male
            }
            else {
                image = R.drawable.icon_female
            }
        }
        return image
    }
    //endregion
}