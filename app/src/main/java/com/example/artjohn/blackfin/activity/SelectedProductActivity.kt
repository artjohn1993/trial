package com.example.artjohn.blackfin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.artjohn.blackfin.R

class SelectedProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_product)
        title = "Select a Product"
    }
}
