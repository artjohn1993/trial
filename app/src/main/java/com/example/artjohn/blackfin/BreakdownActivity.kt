package com.example.artjohn.blackfin

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.artjohn.blackfin.adapter.PremiumAdapter
import com.example.artjohn.blackfin.model.ClientInfo

import com.example.artjohn.blackfin.model.QouteRequest
import com.example.artjohn.blackfin.event.TotalPremium
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_breakdown.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.RoundingMode
import java.text.DecimalFormat


class BreakdownActivity : AppCompatActivity() {
    var imageArray : Array<Int> = arrayOf(
            R.drawable.ic_accuro,
            R.drawable.ic_aia,
            R.drawable.ic_amp,
            R.drawable.ic_amprpp,
            R.drawable.ic_asteron,
            R.drawable.ic_fidelity,
            R.drawable.ic_nib,
            R.drawable.ic_onepath,
            R.drawable.ic_partnerslife,
            R.drawable.ic_southerncross,
            R.drawable.ic_sovereign,
            R.drawable.ic_fidelity
    )
    lateinit var qoute : QouteRequest.Result
    var index : Int = 0
    var id  : Int = 0
    var totalPremium : Double = 0.0

    var age : Int = 0
    var gender : String = ""
    var smoker : String = ""
    var clientClass : String = ""
    var currentStatus : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breakdown)
        title = "Breakdown"
        qoute = Gson().fromJson(intent.extras.get("qoute").toString(),QouteRequest.Result::class.java)
        index = intent.extras.get("position") as Int
        id   = qoute.data.data.result.providers[index].providerId

        bind()
        producPremiumRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        producPremiumRecyclerView.adapter = PremiumAdapter(qoute,index)
    }
    fun bind(){
        setLogo()
        setprofile()
        setPT()
        setDetails()
    }
    fun setPT()
    {
        policyPriceText.text = "$" + qoute.data.data.result.providers[index].policyFee
        totalPremium += qoute.data.data.result.providers[index].policyFee
    }
    fun setLogo()
    {
        logo.setImageResource(imageArray[id-1])
        nameText.text = ClientInfo.array[0].name
    }
    fun setprofile()
    {
        var gender = ClientInfo.array[0].gender
        var age = ClientInfo.array[0].age.toInt()

        if(gender.equals("M"))
        {

                if (age>18)
                {
                    profile.setImageResource(R.drawable.icon_male)
                }
                else
                {
                    profile.setImageResource(R.drawable.icon_boy)
                }
        }
        else
        {

            if (age>18)
            {
                profile.setImageResource(R.drawable.icon_female)
            }
            else
            {
                profile.setImageResource(R.drawable.icon_girl)
            }
        }
    }
    fun setDetails()
    {
        age = ClientInfo.array[0].age.toInt()
        var status = arrayOf("Class 1","Class 2","Class 3","Class 4","Class 5")
        clientClass = status[ClientInfo.array[0].occupationId - 1]
        currentStatus = ClientInfo.array[0].employedStatus
        if(ClientInfo.array[0].isChild){smoker = "Smoker"}
        else {smoker = "Not Smoker"}
        if(ClientInfo.array[0].gender == "M"){gender = "Male"}
        else{gender = "Female"}

        detailsText.text = "$age, $gender, $smoker, $clientClass, $currentStatus"
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProductPremium(event : TotalPremium)
    {
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        totalPremium += event.total

        //totalPremium = (totalPremium*100.0)/100.0
        premiumPriceText.text = "$" + df.format(totalPremium)
    }


    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


}

