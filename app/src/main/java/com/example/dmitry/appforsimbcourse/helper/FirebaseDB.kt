package com.example.dmitry.appforsimbcourse.helper

import android.util.Log
import com.example.dmitry.appforsimbcourse.model.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by dmitry on 23.02.18.
 */
class FirebaseDB {
    private val LOG_TAG = "FirebaseDB"

    val mDatabase:DatabaseReference = FirebaseDatabase.getInstance().reference
    val user_Firebase:FirebaseUser = FirebaseAuth.getInstance().currentUser!!

    fun addNewInDB(user: AppUser) {
        mDatabase.child("users").child(user_Firebase.uid).setValue(user)
    }

}