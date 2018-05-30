package com.example.artjohn.blackfin.dialog

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.ClientInfo
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.renderscript.ScriptGroup
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toolbar
import com.example.artjohn.blackfin.event.EditUser
import com.example.artjohn.blackfin.event.RefreshPeopleEvent
import com.example.artjohn.blackfin.model.ConfigureBenefits
import com.example.artjohn.blackfin.model.Inputs
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.collections.ArrayList


class PeopleDialog {

    //region - Variables
    var dialog : Dialog? = null
    var username : TextView? = null
    var edit : Button? = null
    var delete : Button? = null

    //endregion

    fun show(activity: Activity, index : Int) {
        this.dialog = Dialog(activity)
        this.dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        this.dialog?.setContentView(R.layout.dialog_people_layout)
        dialogViewId(dialog)
        checkPrimaryUser(index)
        username?.text = ClientInfo.array[index].name
        edit?.setOnClickListener {

        }
        delete?.setOnClickListener {
            removeUser(index)
            dialog?.hide()
        }

        edit?.setOnClickListener {
            EventBus.getDefault().post(EditUser(index))
            dialog?.hide()
        }

        this.dialog?.show()
    }

    private fun dialogViewId(dialog: Dialog?) {
        username = dialog?.findViewById(R.id.userName)
        edit = dialog?.findViewById(R.id.editButton)
        delete = dialog?.findViewById(R.id.deleteButton)
    }
    private fun checkPrimaryUser(index : Int) {
        if (index == 0) {
            delete?.visibility = View.GONE
        }
    }
    private fun removeUser(index : Int) {
        var userID : Int = ClientInfo.array[index].clientId.toInt()
        var size : Int = ConfigureBenefits.array.size

        ClientInfo.array.removeAt(index)
        var clientIndex : Int = 0
        ClientInfo.array.forEach { data ->
            ClientInfo.array[clientIndex].clientId = (++clientIndex).toString()
        }
        removeBenefits(userID)
        EventBus.getDefault().post(RefreshPeopleEvent(index))
    }
    private fun removeBenefits(userID : Int) {
        var oldID : Int = userID + 1
        var newID : Int = userID
        var configIndex : Int = 0
        var tempArray : ArrayList<Inputs> = ArrayList()
        ConfigureBenefits.array.forEach { data ->
            var id = data.clientId
            if (id != newID) {
                tempArray.add(ConfigureBenefits.array[configIndex])
            }
            ++configIndex
        }
        ConfigureBenefits.array = tempArray

        ClientInfo.array.forEach { data ->
            configIndex = 0
            ConfigureBenefits.array.forEach { data ->
                print("")
                if (data.clientId == oldID) {
                    ConfigureBenefits.array[configIndex].clientId = newID
                }
                ++configIndex
            }
            newID = oldID
            oldID++
        }
    }
}