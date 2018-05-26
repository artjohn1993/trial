package com.example.artjohn.blackfin.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.presenter.MainPresenter
import com.example.artjohn.blackfin.presenter.MainPresenterClass
import com.example.artjohn.blackfin.presenter.MainView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity(),
        MainView {

    //region - Variables
    val  presenter : MainPresenter by lazy{ MainPresenterClass(this) }
    var smokerChecker : Boolean = false
    var age : String = ""
    var gender : String = ""
    var occupation : Int = 0
    var status : String = ""

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "New Qoute"
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        smokerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            smokerChecker = isChecked
        }

        mainNextButton.setOnClickListener {
            setInformation()
            var intent = Intent(baseContext,BenefitsActivity::class.java)
            intent.putExtra("clientId",1)
            startActivity(intent)
        }

        occupationGuideText.setOnClickListener {
            startActivity<OccupationGuideActivity>()
        }

       bind()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
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
        this.age = ageSpinner.selectedItem.toString()
        this.gender =  IdentifyGender(genderSpinner.selectedItem.toString())
        this.occupation = occupationSpinner.selectedItemPosition + 1
        this.status = statusSpinner.selectedItem.toString()
        this.presenter.saveClientInfo(
                nameEdit.text.toString(),
                true,
                "1",
                smokerChecker,
                age,
                gender,
                false,
                status,
                occupation)
    }
    //endregion

    //region - Presenter Delegates
    override fun setAgeAdapter(data : ArrayList<Int>) {
        val ageAdapter : ArrayAdapter<Int> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        ageSpinner.adapter = ageAdapter
    }

    override fun setStatusAdapter(data: Array<String>) {
        val statusAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        statusSpinner.adapter = statusAdapter
    }

    override fun setOccupationAdapter(data: Array<String>) {
        val occupationAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        occupationSpinner.adapter = occupationAdapter
    }

    override fun setGenderAdapter(data: Array<String>) {
        val genderAdapter : ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)
        genderSpinner.adapter = genderAdapter
    }
    //endregion
}
