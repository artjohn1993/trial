package com.example.artjohn.blackfin

import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.example.artjohn.blackfin.presenter.main.MainMVP
import com.example.artjohn.blackfin.presenter.main.MainPresenter
import com.example.artjohn.blackfin.model.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity(), MainMVP.View
{


    val  presenter : MainPresenter by lazy{ MainPresenter(this) }
    var smokerChecker : Boolean = false

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "New Qoute"
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)


        smokerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            smokerChecker = isChecked
        }

        mainNextButton.setOnClickListener {
            var age = ageSpinner.selectedItem.toString()
            var gender =  IdentifyGender(genderSpinner.selectedItem.toString())
            var occupation = occupationSpinner.selectedItemPosition + 1
            var status = statusSpinner.selectedItem.toString()
            presenter.saveClientInfo(nameEdit.text.toString(),true,"1",smokerChecker,age,gender,false,status,occupation)

            startActivity<BenefitsActivity>()
        }

        occupationGuideText.setOnClickListener {
            startActivity<OccupationGuideActivity>()
        }

        presenter.processAgeAdapter()
        presenter.setAdapters()
    }

    fun IdentifyGender(gender : String) : String
    {
        return if(gender == "Male")
        {
            "M"
        }
        else
        {
            "F"
        }
    }


    override fun onPause()
    {
        super.onPause()
       compositeDisposable.clear()
    }

    override fun setAgeAdapter(data : ArrayList<Int>)
    {
        val ageAdapter : ArrayAdapter<Int> = ArrayAdapter(this, android.R.layout.simple_list_item_1,data)
        ageSpinner.adapter = ageAdapter
    }
    override fun setStatusAdapter(data: Array<String>)
    {
        val statusAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1,data)
        statusSpinner.adapter = statusAdapter
    }

    override fun setOccupationAdapter(data: Array<String>)
    {
        val occupationAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1,data)
        occupationSpinner.adapter = occupationAdapter
    }

    override fun setGenderAdapter(data: Array<String>)
    {
        val genderAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1,data)
        genderSpinner.adapter = genderAdapter
    }
}
