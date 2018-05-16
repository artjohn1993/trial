package com.example.artjohn.blackfin.presenter.breakdown

import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.QouteRequest
import kotlinx.android.synthetic.main.activity_breakdown.*
import java.math.RoundingMode
import java.text.DecimalFormat

class BreakDownPresenter(val view : BreakDownMVP.View) : BreakDownMVP.Presenter
{



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
    override fun processProfile()
    {
        var gender = ClientInfo.array[0].gender
        var age = ClientInfo.array[0].age.toInt()


        if(gender.equals("M"))
        {

            if (age>18)
            {
                view.setProfile(R.drawable.icon_male)
            }
            else
            {

                view.setProfile(R.drawable.icon_boy)
            }
        }
        else
        {

            if (age>18)
            {
                view.setProfile(R.drawable.icon_female)
            }
            else
            {
                view.setProfile(R.drawable.icon_girl)
            }
        }
    }

    override fun processLogo(id : Int)
    {
        view.setLogo(imageArray[id-1])
    }

    override fun processDetails()
    {
        var name = ClientInfo.array[0].name
        var age = ClientInfo.array[0].age.toInt()
        var status = arrayOf("Class 1","Class 2","Class 3","Class 4","Class 5")
        var smoker = ""
        var  gender = ""

       var clientClass = status[ClientInfo.array[0].occupationId - 1]
        var currentStatus = ClientInfo.array[0].employedStatus

        if(ClientInfo.array[0].isChild){smoker = "Smoker"}
        else {smoker = "Not Smoker"}
        if(ClientInfo.array[0].gender == "M"){gender = "Male"}
        else{gender = "Female"}

        view.setDetails(name,age,gender,smoker,clientClass,currentStatus)

    }

    override fun processPremiumPrice(price: Double)
    {
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        view.setPremiumPrice(df.format(price))
    }
    override fun processPT(data: QouteRequest.Result,index : Int)
    {
        view.setPT(data.data.data.result.providers[index].policyFee)
    }
}