package com.example.artjohn.blackfin.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.presenter.AddChildClass
import com.example.artjohn.blackfin.presenter.AddChildView
import com.example.artjohn.blackfin.presenter.AddClientPresenter
import com.example.artjohn.blackfin.presenter.AddClientView
import kotlinx.android.synthetic.main.activity_add_child.*
import kotlinx.android.synthetic.main.activity_add_client.*
import org.jetbrains.anko.startActivity

class AddChildActivity : BaseActivity(), AddChildView {

    //region - Variables
    var presenter = AddChildClass(this)
    var age : String = ""
    var gender : String = ""
    var name : String = ""
    var clientID : String = ""
    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_child)
        title = "Add Child"
        this.clientID = getID()
        presenter.setAdapter()

        applyChildButton.setOnClickListener {
            setInformation()
            var intent = Intent(this, BenefitsActivity::class.java)
            intent.putExtra("clientId",clientID)
            startActivity(intent)
            finish()
        }
    }
    //endregion

    //region - Presenter Delegates
    override fun setAge(data: ArrayList<Int>) {
        val ageAdapter : ArrayAdapter<Int> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        childAgeSpinner.adapter = ageAdapter
    }

    override fun setGender(data: Array<String>) {
        val genderAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        childGenderSpinner.adapter = genderAdapter
    }
    //endregion

    //region - Private methods
    private fun IdentifyGender(gender : String) : String {
        return if(gender == "Male") {
            "M"
        }
        else {
            "F"
        }
    }
    private fun setInformation() {
        this.age = childAgeSpinner.selectedItem.toString()
        this.gender =  IdentifyGender(childGenderSpinner.selectedItem.toString())
        this.name = childNameEdit.text.toString()
        this.presenter.saveClientInfo(name, false, clientID, false, age, gender, true, "", 0)
    }
    private fun getID() : String {
        var id : Int = ClientInfo.array.size
        id += 1
        return id.toString()
    }
    //endregion
}
