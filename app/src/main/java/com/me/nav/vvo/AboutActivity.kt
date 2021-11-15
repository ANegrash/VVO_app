package com.me.nav.vvo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class AboutActivity : AppCompatActivity() {
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val backBtn = findViewById<ImageButton>(R.id.btn_about_back)

        backBtn.setOnClickListener {
            finish()
        }
    }
}