package com.example.artjohn.blackfin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.GridLayout
import com.example.artjohn.blackfin.adapter.BenefitsAdapter
import com.example.artjohn.blackfin.dialog.HealthDialog
import com.example.artjohn.blackfin.dialog.LifeDialog
import com.example.artjohn.blackfin.model.ClientInfo
import com.example.artjohn.blackfin.model.Qoute
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_benefits.*

class BenefitsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_benefits)
        title = "Benefits"
        benefitsRecyclerView.layoutManager = GridLayoutManager(this,2)
        benefitsRecyclerView.adapter = BenefitsAdapter(this)

        println(ClientInfo.array)

        benefitsNextButton.setOnClickListener {

        }

    }
}
