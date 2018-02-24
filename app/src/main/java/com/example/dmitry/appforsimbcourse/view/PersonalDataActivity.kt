package com.example.dmitry.appforsimbcourse.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.dmitry.appforsimbcourse.R
import com.example.dmitry.appforsimbcourse.interfaces.IMyActivity
import com.example.dmitry.appforsimbcourse.model.AppUser
import com.example.dmitry.appforsimbcourse.presenter.PresenterPersonalData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class PersonalDataActivity : AppCompatActivity(), View.OnClickListener, IMyActivity {

    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var btnSave: Button
    private val presenterPersonalData:PresenterPersonalData = PresenterPersonalData(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        name = findViewById(R.id.actPersonalDataName)
        email = findViewById(R.id.actPersonalDataEmail)
        phone = findViewById(R.id.actPersonalDataPhone)
        btnSave = findViewById(R.id.actPersonalDataBtnSave)

        presenterPersonalData.getUsersInfo()

        btnSave.setOnClickListener(this)

        presenterPersonalData.setMaskPhone(phone)

    }

    override fun onClick(p0: View?) {
        if (name.text.toString().isNotEmpty() &&
                presenterPersonalData.emailIsValid(email.text.toString())) {
            presenterPersonalData.updateDataServer(email.text.toString(), phone.text.toString(),
                    name.text.toString())
            presenterPersonalData.startProfileActivity(this)
        }

    }

    override fun updateUI(appUser: AppUser) {
        name.text.insert(0,appUser.name)
        email.text.insert(0,appUser.email)
        phone.text.insert(0,appUser.phone)
    }




}
