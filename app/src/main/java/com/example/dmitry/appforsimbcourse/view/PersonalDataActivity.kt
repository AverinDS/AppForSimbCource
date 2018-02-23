package com.example.dmitry.appforsimbcourse.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.dmitry.appforsimbcourse.R
import com.example.dmitry.appforsimbcourse.presenter.PresenterPersonalData

class PersonalDataActivity : AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var phone: EditText
    private val presenterPersonalData:PresenterPersonalData = PresenterPersonalData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        name = findViewById(R.id.actPersonalDataEmail)
        name.text = intent.getStringExtra("email")

        phone = findViewById(R.id.actPersonalDataPhone)
        presenterPersonalData.setMaskPhone(phone)
    }
}
