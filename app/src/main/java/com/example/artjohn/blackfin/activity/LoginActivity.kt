package com.example.artjohn.blackfin.activity

import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.artjohn.blackfin.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    //region - Variables
    lateinit var logoAnimation : Animation
    lateinit var fadeout : Animation
    lateinit var slideUp : Animation
    //endregion

    //region - Lifecycle methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout_animation)
        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_animation)
        signUpButton.setOnClickListener {
            logo.startAnimation(logoAnimation)
            firstGroup.startAnimation(fadeout)
            secondGroup.visibility = View.VISIBLE
            secondGroup.startAnimation(slideUp)
        }
    }
    //endregion
}
