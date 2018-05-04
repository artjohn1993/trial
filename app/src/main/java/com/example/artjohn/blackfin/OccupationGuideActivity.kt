package com.example.artjohn.blackfin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.example.artjohn.blackfin.adapter.OccupationGuideAdapter
import kotlinx.android.synthetic.main.activity_occupation_guide.*

class OccupationGuideActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_occupation_guide)
        occupationGuideRecycleView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,false)
        occupationGuideRecycleView.adapter = OccupationGuideAdapter(this)
    }
}
