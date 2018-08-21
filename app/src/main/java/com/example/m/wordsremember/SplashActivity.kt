package com.example.m.wordsremember

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.m.wordsremember.ui.login.AuthActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            AuthActivity.start(this@SplashActivity)
            finish()
        }, 2000)

    }
}
