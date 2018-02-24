package com.example.dmitry.appforsimbcourse.helper

import com.example.dmitry.appforsimbcourse.model.AppUser
import com.example.dmitry.appforsimbcourse.interfaces.IMyPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

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

    fun getUsersInfo(presenter: IMyPresenter) {
        mDatabase.child("users").child(user_Firebase.uid)
                .addListenerForSingleValueEvent(
                        object: ValueEventListener {

                            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                                val appUser:AppUser = dataSnapshot!!.getValue(AppUser::class.java)!!
                                presenter.updateUI(appUser)
                            }

                            override fun onCancelled(p0: DatabaseError?) {

                            }
                        }
                )
    }

}