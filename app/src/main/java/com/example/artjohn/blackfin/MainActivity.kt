package com.example.artjohn.blackfin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.text.ParcelableSpan
import android.view.View
import android.widget.ArrayAdapter
import com.example.artjohn.blackfin.api.BlackfinApi
import com.example.artjohn.blackfin.model.*
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.occupation_guide_layout.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe



class MainActivity : AppCompatActivity() {

    var ageArray = arrayListOf<Int>()
    var smokerChecker : Boolean = false

    private var disposable : Disposable? = null
    private var compositeDisposable : CompositeDisposable? = CompositeDisposable()
    private val apiServer by lazy {
        BlackfinApi.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "New Qoute"
        /*this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)*/


        smokerSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            smokerChecker = isChecked

        }

        mainNextButton.setOnClickListener {
            //EventBus.getDefault().post(SignIn("droid1-qatest@mail.com","firebrand",true))
            println("================onSignIn===================")
            var data = SignIn("droid1-qatest@mail.com","firebrand",true)
            compositeDisposable?.add(
                    apiServer.login(data)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe({result ->
                                    UserInformation(result.data.authorization.token,result.data.user.id)
                            },{
                                error ->
                                print(error.toString())
                            })
            )

            println("================End of Sign in===================")

            saveClientInfo()
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
    fun saveClientInfo()
    {

        var age = ageSpinner.selectedItem.toString()
        var gender =  IdentifyGender(genderSpinner.selectedItem.toString())
        var occupation = occupationSpinner.selectedItemPosition
        var status = statusSpinner.selectedItem.toString()


        var array : ArrayList<Qoute.ClientsInformation> = ArrayList()
        var qoute  = Qoute.ClientsInformation(nameEdit.text.toString(),true,"1",smokerChecker,age,gender,false,status,occupation)
        array.add(qoute)
        ClientInfo(array)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSignIn(event: SignIn)
    {
        println("================onSignIn===================")
        compositeDisposable?.add(
                apiServer.Logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({result ->
                    result.message
                },{
                    error ->
                    print(error.toString())
                })
        )



        println("================End of Sign in===================")
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        compositeDisposable?.clear()
        EventBus.getDefault().unregister(this)
    }

    override fun onPause() {
        super.onPause()
       compositeDisposable?.clear()
    }
}
