package com.example.dmitry.appforsimbcourse.presenter

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import com.example.dmitry.appforsimbcourse.view.SignInOrUpActivity
import com.example.dmitry.appforsimbcourse.view.PersonalDataActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by dmitry on 14.02.18.
 */
class PresenterSignInOrUpAct {

    private val LOG_TAG: String = "PresenterSignInOrUpAct"


    fun login(activity: SignInOrUpActivity, mAuth: FirebaseAuth, email: String, passw: String) {
        mAuth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        Log.d(LOG_TAG, "SignIn: Success")
                        mAuth.currentUser
                        startPersonalDataActivity(activity)
                    } else {
                        Log.d(LOG_TAG, "SignIn: Fail" + task.exception.toString())
                        Toast.makeText(activity, "Ошибка авторизации", Toast.LENGTH_LONG).show()
                        activity.getPasswordField().error = "Неверный пароль"
                    }
                }
    }

    fun signUp(activity: Activity, mAuth: FirebaseAuth, email: String, passw: String) {
        mAuth.createUserWithEmailAndPassword(email, passw)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        Log.d(LOG_TAG, "createUser: Success ")
                        Toast.makeText(activity, "Вы успешно зарегистрировались",
                                Toast.LENGTH_LONG).show()
                        mAuth.currentUser
                        startPersonalDataActivity(activity)
                    } else {
                        Log.d(LOG_TAG, "createUser: Fail " + task.exception.toString())
                        Toast.makeText(activity, "Ошибка регистрации", Toast.LENGTH_LONG).show()
                    }
                }
    }

    fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun startPersonalDataActivity(parent: Activity) {
        val intent: Intent = Intent(parent, PersonalDataActivity::class.java)
        startActivity(parent, intent, null)
    }
}