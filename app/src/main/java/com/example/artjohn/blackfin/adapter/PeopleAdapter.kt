package com.example.artjohn.blackfin.adapter

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.dialog.PeopleDialog
import com.example.artjohn.blackfin.event.ConfigureClient
import com.example.artjohn.blackfin.event.EditUserBenefits
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.ClientsInformation
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.Inputs
import kotlinx.android.synthetic.main.layout_people.view.*
import org.greenrobot.eventbus.EventBus

class PeopleAdapter() : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

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
        return ClientInfo.array.size
    }

    override fun onBindViewHolder(holder: PeopleAdapter.PeopleViewHolder,
                                  position: Int) {

        setDetails(position)

        holder.name.text = ClientInfo.array[position].name
        holder.details.text = "$age, $gender, $smoker, $userClass"
        holder.benefits.text = setBenefits(position)
        holder.profile.setImageResource(setProfilePic(ClientInfo.array[position].age.toInt(), ClientInfo.array[position].gender))

        holder.more.setOnClickListener {
            EventBus.getDefault().post(ConfigureClient(position))
        }

        holder.container.setOnClickListener {
            EventBus.getDefault().post(EditUserBenefits(position.plus(1)))
        }

    }
    //endregion

    //region - View Holder
    class PeopleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var profile : ImageView = itemView.findViewById(R.id.profilePic)
        var name : TextView = itemView.findViewById(R.id.nameText)
        var details : TextView = itemView.findViewById(R.id.detailsText)
        var benefits : TextView = itemView.findViewById(R.id.benefitsValueText)
        var more : ImageButton = itemView.findViewById(R.id.moreButton)
        var container : android.support.v7.widget.CardView = itemView.findViewById(R.id.peopleContainer)
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
        age = ClientInfo.array[position].age.toInt()
        gender = ClientInfo.array[position].gender
        smoker = smoker(ClientInfo.array[position].isSmoker)
        userClass = ClientInfo.array[position].employedStatus
    }
    private fun setBenefits(position: Int) : String {
        var data : String = ""
        var userID : Int = position.plus(1)
        for (index in 0 until ConfigureBenefits.array.size ) {
            var clientID : Int = ConfigureBenefits.array[index].clientId
            if (clientID == userID) {
                if (data == "") {
                    data = ConfigureBenefits.array[index].inputs.benefitsType
                }
                else {
                    data += ", " + ConfigureBenefits.array[index].inputs.benefitsType
                }
            }
        }
        return data
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