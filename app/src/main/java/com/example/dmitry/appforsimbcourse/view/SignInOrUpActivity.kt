package com.example.dmitry.appforsimbcourse.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dmitry.appforsimbcourse.R
import com.example.dmitry.appforsimbcourse.presenter.PresenterSignInOrUpAct
import com.google.firebase.auth.FirebaseAuth


class SignInOrUpActivity : AppCompatActivity() {

    private val minLengthPassword: Int = 8
    private lateinit var mAuth: FirebaseAuth
    private lateinit var buttonSignIn: Button
    private lateinit var buttonSignUp: Button
    private lateinit var login: EditText
    private lateinit var passw: EditText


    private var presenter: PresenterSignInOrUpAct = PresenterSignInOrUpAct()
    private var isCorrectLog: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setActionBar()//title and back

        buttonSignIn = findViewById(R.id.actMainBtnSignIn)
        buttonSignUp = findViewById(R.id.actMainBtnSignUp)
        login = findViewById(R.id.actMainLogin)
        passw = findViewById(R.id.actMainPassw)

        this.mAuth = FirebaseAuth.getInstance()

        //пока что регистрацию сделаю на той же форме, что и авторизацию
        buttonSignUp.setOnClickListener({
            if (isCorrectLog.and(passw.text.toString().length >= this.minLengthPassword)) {
                presenter.signUp(this, mAuth, login.text.toString(), passw.text.toString())
            } else {
                Toast.makeText(this, "Неправильные данные", Toast.LENGTH_SHORT).show()
            }
        })

        buttonSignIn.setOnClickListener({
            if (isCorrectLog.and(passw.text.toString().length >= this.minLengthPassword)) {
                presenter.login(this, mAuth, login.text.toString(), passw.text.toString())
            } else {
                Toast.makeText(this, "Неправильные данные", Toast.LENGTH_SHORT).show()
            }
        })

        login.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (presenter.isEmailValid(login.text.toString())) {
                    isCorrectLog = true
                    changeButtonSignIn(true)
                } else {
                    isCorrectLog = false
                    changeButtonSignIn(false)
                }
            }

        })
    }

    override fun onStart() {
        super.onStart()
        //val currentUser = mAuth.currentUser
    }

    fun setActionBar() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = resources.getString(R.string.auth)
    }


    fun changeButtonSignIn(couldSignIn: Boolean) {
        when (couldSignIn) {
            true -> {
                buttonSignIn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen))
                buttonSignIn.text = resources.getText(R.string.sign_in)
            }
            false -> {
                buttonSignIn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGray))
                buttonSignIn.text = resources.getText(R.string.not_all_fields)
            }
        }
    }

    fun getPasswordField():EditText {
       return this.passw
    }
}
