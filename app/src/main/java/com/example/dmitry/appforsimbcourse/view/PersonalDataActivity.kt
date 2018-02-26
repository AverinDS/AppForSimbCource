package com.example.dmitry.appforsimbcourse.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
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
    private lateinit var changePhoto: TextView
    private val presenterPersonalData:PresenterPersonalData = PresenterPersonalData(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        name = findViewById(R.id.actPersonalDataName)
        email = findViewById(R.id.actPersonalDataEmail)
        phone = findViewById(R.id.actPersonalDataPhone)
        btnSave = findViewById(R.id.actPersonalDataBtnSave)
        changePhoto = findViewById(R.id.actPersonalDataChangePhoto)

        presenterPersonalData.getUsersInfo()

        btnSave.setOnClickListener(this)
        changePhoto.setOnClickListener{v -> changePhoto(v)}

        presenterPersonalData.setMaskPhone(phone)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menuCamera -> {
                presenterPersonalData.getPhotoFromCamera()
            }
            R.id.menuGallery -> {
                presenterPersonalData.getPhotoFromGallery()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }




    }

    override fun onClick(p0: View?) {
        if (name.text.toString().isNotEmpty() &&
                presenterPersonalData.emailIsValid(email.text.toString())) {
            presenterPersonalData.updateDataServer(email.text.toString(), phone.text.toString(),
                    name.text.toString())
            presenterPersonalData.startProfileActivity(this)
        } else {
            Toast.makeText(this,"Неправильно введенные данные", Toast.LENGTH_SHORT).show()
        }

    }

    override fun updateUI(appUser: AppUser) {
        name.text.insert(0,appUser.name)
        email.text.insert(0,appUser.email)
        phone.text.insert(0,appUser.phone)
    }

    fun changePhoto(view:View) {
        val popUpMenu = PopupMenu(this, view)
        val menuInf:MenuInflater = popUpMenu.menuInflater
        menuInf.inflate(R.menu.actions,popUpMenu.menu)
        popUpMenu.show()
    }




}
