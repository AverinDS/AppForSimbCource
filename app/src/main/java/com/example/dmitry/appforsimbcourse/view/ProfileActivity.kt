package com.example.dmitry.appforsimbcourse.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dmitry.appforsimbcourse.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var name:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        name = findViewById(R.id.actProfileName)
        name.text = ("Добро пожаловать, " + intent.getStringExtra("email") +
                "! Тут потом будет окно профиля ")
    }
}
