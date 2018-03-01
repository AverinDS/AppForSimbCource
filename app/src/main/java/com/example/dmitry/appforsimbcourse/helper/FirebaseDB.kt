package com.example.dmitry.appforsimbcourse.helper

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.example.dmitry.appforsimbcourse.model.AppUser
import com.example.dmitry.appforsimbcourse.interfaces.IMyPresenter
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.Exception

/**
 * Created by dmitry on 23.02.18.
 */
class FirebaseDB {
    private val LOG_TAG = "FirebaseDB"

    val mDatabase:DatabaseReference = FirebaseDatabase.getInstance().reference
    val user_Firebase:FirebaseUser = FirebaseAuth.getInstance().currentUser!!
    val storage:FirebaseStorage = FirebaseStorage.getInstance()

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

    fun saveImageToDatabase(image:ImageView, presenter: IMyPresenter) {
        val storageRef:StorageReference = storage.reference

        val refToImage:StorageReference = storageRef.child(user_Firebase.uid)

        val bitmap: Bitmap
        val stream = ByteArrayOutputStream()
        val mass:ByteArray
        val uploadTask:UploadTask
//        var downloadUrl:Uri

        image.isDrawingCacheEnabled = true
        image.buildDrawingCache()

        bitmap = image.drawingCache
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        mass = stream.toByteArray()

        uploadTask = refToImage.putBytes(mass)
        uploadTask.addOnFailureListener {
            Log.e(LOG_TAG, it.message)
        }.addOnSuccessListener { OnSuccessListener<UploadTask.TaskSnapshot> {
//                    downloadUrl = it.downloadUrl!!
                    presenter.updateUrlPhoto(it.downloadUrl!!)
//                    user_Firebase.photoUrl = it.downloadUrl!!

                } }




    }

}