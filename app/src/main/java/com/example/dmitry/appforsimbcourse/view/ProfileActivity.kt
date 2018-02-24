package com.example.dmitry.appforsimbcourse.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dmitry.appforsimbcourse.R
import com.example.dmitry.appforsimbcourse.interfaces.IMyActivity
import com.example.dmitry.appforsimbcourse.model.AppUser
import com.example.dmitry.appforsimbcourse.presenter.PresenterProfile

class ProfileActivity : AppCompatActivity(), IMyActivity {

    private val presenter: PresenterProfile = PresenterProfile(this)

    private lateinit var name: TextView
    private lateinit var phone: TextView
    private lateinit var email: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        email = findViewById(R.id.actProfileEmail)
        name = findViewById(R.id.actProfileName)
        phone = findViewById(R.id.actProfilePhoneNumb)

        presenter.getUsersInfo()
    }

    override fun updateUI(appUser: AppUser) {
        email.text = appUser.email
        name.text = appUser.name
        phone.text = appUser.phone
    }
}
