package com.example.artjohn.blackfin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.artjohn.blackfin.R
import com.example.artjohn.blackfin.adapter.PeopleAdapter
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.ConfigureBenefits
import kotlinx.android.synthetic.main.activity_people.*

class PeopleActivity : BaseActivity() {

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people)
        title = "People"
        setRecyclerView()
    }
    //endregion

    //region - Private methods
    private fun setRecyclerView() {
        peopleRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        peopleRecyclerView.adapter = PeopleAdapter(ClientInfo.array, ConfigureBenefits.array)
    }
    //
}
