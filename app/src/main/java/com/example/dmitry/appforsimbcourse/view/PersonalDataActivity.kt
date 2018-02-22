package com.example.dmitry.appforsimbcourse.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dmitry.appforsimbcourse.R

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        name = findViewById(R.id.actPersonalEmail)
        name.text = intent.getStringExtra("email")
    }
}
