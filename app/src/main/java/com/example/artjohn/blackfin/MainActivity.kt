package com.example.artjohn.blackfin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.text.ParcelableSpan
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.model.Age
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.Qoute
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.occupation_guide_layout.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    var ageArray = arrayListOf<Int>()
    var smokerChecker : Boolean = false

    private var disposable : Disposable? = null
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "New Qoute"
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        smokerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            smokerChecker = isChecked
        }

        mainNextButton.setOnClickListener {
            /*startActivity<BenefitsActivity>()*/


            var age = ageSpinner.selectedItem.toString()
            var gender =  IdentifyGender(genderSpinner.selectedItem.toString())
            var occupation = occupationSpinner.selectedItemPosition
            var status = statusSpinner.selectedItem.toString()



            var array : ArrayList<Qoute.ClientsInformation> = ArrayList()
            var qoute  = Qoute.ClientsInformation(nameEdit.text.toString(),true,"1",smokerChecker,age,gender,false,status,occupation)
            array.add(qoute)

            ClientInfo(array)

           /* var intent = Intent(baseContext,BenefitsActivity::class.java)
            intent.putParcelableArrayListExtra("client_info", Parcel.obtain())

            startActivity(intent)*/

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
    fun IdentifyGender(gender : String) : String{
        if(gender.equals("Male"))
        {
            return "M"
        }
        else
        {
            return "F"
        }
    }
    fun login()
    {

    }


    override fun onPause() {
        super.onPause()
        disposable?.dispose()

    }
}
