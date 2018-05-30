package com.example.artjohn.blackfin.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.presenter.AddClientClass
import com.example.artjohn.blackfin.presenter.AddClientPresenter
import com.example.artjohn.blackfin.presenter.AddClientView
import kotlinx.android.synthetic.main.activity_add_client.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class AddClientActivity : BaseActivity(), AddClientView {

    //region - Variables
    var presenter = AddClientClass(this)
    var smokerChecker : Boolean = false
    var age : String = ""
    var gender : String = ""
    var occupation : Int = 0
    var status : String = ""
    var clientID : String = ""
    var name : String = ""
    var editMode : Boolean = false


    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        this.clientID = getID()
        title = "Add Client"
        bind()
        addOccupationGuideText.setOnClickListener {
            startActivity<OccupationGuideActivity>()
        }
        applyButton.setOnClickListener {

            if (editMode) {
                setInformation()
                startActivity<PeopleActivity>()
                finish()
            }
            else {
                setInformation()
                var intent = Intent(this, BenefitsActivity::class.java)
                intent.putExtra("clientId",clientID)
                startActivity(intent)
                finish()
            }
        }
        addSmokerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            smokerChecker = isChecked
        }
    }

    override fun onResume() {
        super.onResume()
        checkEdit()
    }
    //endregion

    //region - Presenter Delegate
    override fun setAgeAdapter(data : ArrayList<Int>) {
        val ageAdapter : ArrayAdapter<Int> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        addAgeSpinner.adapter = ageAdapter
    }
    override fun setStatusAdapter(data: Array<String>) {
        val statusAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)

        addStatusSpinner.adapter = statusAdapter
    }

    override fun setOccupationAdapter(data: Array<String>) {
        val occupationAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        addOccupationSpinner.adapter = occupationAdapter
    }

    override fun setGenderAdapter(data: Array<String>) {
        val genderAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        addGenderSpinner.adapter = genderAdapter
    }
    //endregion

    //region - Private methods
    private fun bind() {
        presenter.processAgeAdapter()
        presenter.setAdapters()
    }
    private fun IdentifyGender(gender : String) : String {
        return if(gender == "Male") {
            "M"
        }
        else {
            "F"
        }
    }
    private fun setInformation() {
        this.age = addAgeSpinner.selectedItem.toString()
        this.gender =  IdentifyGender(addGenderSpinner.selectedItem.toString())
        this.occupation = addOccupationSpinner.selectedItemPosition + 1
        this.status = addStatusSpinner.selectedItem.toString()
        this.name = addNameEdit.text.toString()
        this.presenter.saveClientInfo(name, false, clientID, smokerChecker, age, gender, false, status, occupation)
    }
    private fun getID() : String {
        var id : Int = ClientInfo.array.size
        id += 1
        return id.toString()
    }
    private fun checkEdit() {
        var extra = intent.extras
        if (extra != null) {
            var id = extra.getInt("edit_user")
            addNameEdit.setText(ClientInfo.array[id].name)
            addAgeSpinner.setSelection(ClientInfo.array[id].age.toInt() - 18)
            if (ClientInfo.array[id].gender == "M") {
                addGenderSpinner.setSelection(0)
            }
            else {
                addGenderSpinner.setSelection(1)
            }
            addSmokerSwitch.isChecked = ClientInfo.array[id].isSmoker
            addOccupationSpinner.setSelection(ClientInfo.array[id].occupationId - 1)
            var status = arrayOf(
                    "Employed",
                    "Self-Employed",
                    "Self-Employed < 3 years",
                    "Non-Earner")
            addStatusSpinner.setSelection(status.indexOf(ClientInfo.array[id].employedStatus))

            clientID = ClientInfo.array[id].clientId
            editMode = true

        }
    }
    //endregion
}
