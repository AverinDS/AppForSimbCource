package com.example.dmitry.appforsimbcourse.view

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.dmitry.appforsimbcourse.R
import com.example.dmitry.appforsimbcourse.interfaces.IMyActivity
import com.example.dmitry.appforsimbcourse.model.AppUser
import com.example.dmitry.appforsimbcourse.presenter.PresenterProfile


class ProfileActivity : AppCompatActivity(), IMyActivity {
    private val presenter: PresenterProfile = PresenterProfile(this)

    private lateinit var name: TextView
    private lateinit var phone: TextView
    private lateinit var email: TextView
    private lateinit var avatar: ImageView
    private lateinit var exitPic: ImageView
    private lateinit var exitText: TextView
    private lateinit var edit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        email = findViewById(R.id.actProfileEmail)
        name = findViewById(R.id.actProfileName)
        phone = findViewById(R.id.actProfilePhoneNumb)
        exitPic = findViewById(R.id.actProfileExitPic)
        exitText = findViewById(R.id.actProfileExitText)
        edit = findViewById(R.id.actProfileEdit)
        avatar = findViewById(R.id.actProfileAvatar)


        exitText.setOnClickListener{v->finish()}
        exitPic.setOnClickListener{v->finish()}
        edit.setOnClickListener{v-> presenter.startPersonalDataActivity(this)}

        presenter.downloadPhoto()
        presenter.getUsersInfo()

    }

    override fun updateUI(appUser: AppUser) {
        email.text = appUser.email
        name.text = appUser.name
        phone.text = appUser.phone
    }

    private fun setAvatar(bitmap: Bitmap) {
        avatar.setImageBitmap(bitmap)
    }

    override fun onDownloadPhoto(bitmap: Bitmap) {
        setAvatar(bitmap)
    }

    override fun onFailureDownloadPhoto(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
}
