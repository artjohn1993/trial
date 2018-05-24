package com.example.artjohn.blackfin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.artjohn.blackfin.R
import kotlinx.android.synthetic.main.activity_add.*
import org.jetbrains.anko.startActivity

class AddActivity : BaseActivity() {

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        supportActionBar?.hide()

        adultImage.setOnClickListener {
            startActivity<AddClientActivity>()
        }

        cancelButton.setOnClickListener {
            onSupportNavigateUp()
        }
    }
    //endregion

    //region - Navigation Delegates
    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }
    //endregion
}
