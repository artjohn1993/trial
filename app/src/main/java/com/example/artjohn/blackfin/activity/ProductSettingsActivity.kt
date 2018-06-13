package com.example.artjohn.blackfin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.artjohn.blackfin.R

class ProductSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_settings)
        title = "Product Settings"
    }
}
