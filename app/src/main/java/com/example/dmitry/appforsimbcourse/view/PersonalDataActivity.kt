package com.example.dmitry.appforsimbcourse.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dmitry.appforsimbcourse.R
import com.example.dmitry.appforsimbcourse.presenter.PresenterPersonalData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class PersonalDataActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var name: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var btnSave: Button
    private val presenterPersonalData:PresenterPersonalData = PresenterPersonalData()
    private val user:FirebaseUser= FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        name = findViewById(R.id.actPersonalDataName)
        email = findViewById(R.id.actPersonalDataEmail)
        phone = findViewById(R.id.actPersonalDataPhone)
        btnSave = findViewById(R.id.actPersonalDataBtnSave)


        email.text.insert(0, if(user.email != null) user.email else "")
        name.text.insert(0, if(user.displayName != null) user.displayName else "")
        phone.text.insert(0, if(user.phoneNumber != null) user.phoneNumber else "")

        btnSave.setOnClickListener(this)

        presenterPersonalData.setMaskPhone(phone)
    }

    override fun onClick(p0: View?) {
        if (name.text.toString().isNotEmpty() &&
                presenterPersonalData.emailIsValid(email.text.toString())) {
            presenterPersonalData.updateDataServer(email.text.toString(), phone.text.toString(),
                    name.text.toString())
        }

    }


}
