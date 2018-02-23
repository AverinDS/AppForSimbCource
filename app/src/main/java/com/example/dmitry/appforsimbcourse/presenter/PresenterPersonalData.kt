package com.example.dmitry.appforsimbcourse.presenter

import android.widget.EditText
import com.example.dmitry.appforsimbcourse.helper.FirebaseDB
import com.example.dmitry.appforsimbcourse.model.AppUser
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

/**
 * Created by dmitry on 23.02.18.
 */
class PresenterPersonalData {
    private val LOG_TAG:String = "PresenterPersonalData"

    fun emailIsValid(email:String):Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun setMaskPhone(editText:EditText) {
        val formatWatcher:FormatWatcher = MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER))
        formatWatcher.installOn(editText)
    }

    fun updateDataServer(email:String, phone:String, name:String) {
        val dbHelper = FirebaseDB()
        dbHelper.addNewInDB(AppUser(email,phone,name))

//        val user:FirebaseUser = FirebaseAuth.getInstance().currentUser!!
//        val database:DatabaseReference = FirebaseDatabase.getInstance().reference
//
//        database.child("users").child(user.uid)
//                .addListenerForSingleValueEvent(
//                        object: ValueEventListener{
//                            override fun onCancelled(p0: DatabaseError?) {
////                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                            }
//
//                            override fun onDataChange(dataSnapshot: DataSnapshot?) {
//                                val customUser:User = dataSnapshot!!.getValue(User::class.java)!!
//                            }
//                        }
//                )
//

    }
}