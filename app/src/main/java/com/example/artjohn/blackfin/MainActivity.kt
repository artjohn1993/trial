package com.example.artjohn.blackfin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.model.Age
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.occupation_guide_layout.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    var ageArray = arrayListOf<Int>()


    private var disposable : Disposable? = null
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "New Qoute"

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        mainNextButton.setOnClickListener {
            startActivity<BenefitsActivity>()
        }

        occupationGuideText.setOnClickListener {
            startActivity<OccupationGuideActivity>()
        }
        setSpinner()
    }


    fun setSpinner(){
        var status = arrayOf("Employed","Self-Employed","Self-Employed < 3 years","Non-Earner")
        val statusAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1,status)
        statusSpinner.adapter = statusAdapter

        var occupation = arrayOf("Class 1","Class 2","Class 3","Class 4","Class 5")
        val occupationAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1,occupation)
        occupationSpinner.adapter = occupationAdapter

        var gender = arrayOf("Male","Female")
        val genderAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1,gender)
        genderSpinner.adapter = genderAdapter

        ageSpinner.adapter = setAgeAdapter()

    }
    fun setAgeAdapter() : ArrayAdapter<Int>{
        val age = ArrayList<Int>()
        for(i in 0..75)
        {
            age.add(i)
        }
        val ageAdapter : ArrayAdapter<Int> = ArrayAdapter(this, android.R.layout.simple_list_item_1,age)

        return ageAdapter
    }
    fun login()
    {

    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()

    }
}
