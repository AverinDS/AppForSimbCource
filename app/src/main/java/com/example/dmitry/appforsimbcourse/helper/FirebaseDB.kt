package com.example.dmitry.appforsimbcourse.helper

import android.graphics.Bitmap
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
import java.io.ByteArrayOutputStream

/**
 * Created by dmitry on 23.02.18.
 */
class FirebaseDB {
    private val LOG_TAG = "FirebaseDB"

    private val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val userFirebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference
    private val refToImage: StorageReference = storageRef.child(userFirebase.uid)
    private val oneMegabyte: Long = 1024 * 1024

    fun addNewInDB(user: AppUser) {
        mDatabase.child("users").child(userFirebase.uid).setValue(user)
    }

    fun getUsersInfo(presenter: IMyPresenter) {
        mDatabase.child("users").child(userFirebase.uid)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                                val appUser: AppUser = dataSnapshot!!.getValue(AppUser::class.java)!!
                                presenter.updateUI(appUser)
                            }

                            override fun onCancelled(p0: DatabaseError?) {}
                        }
                )
    }

    fun saveImageToDatabase(image: ImageView) {
        val bitmap: Bitmap
        val stream = ByteArrayOutputStream()
        val mass: ByteArray
        val uploadTask: UploadTask

        image.isDrawingCacheEnabled = true
        image.buildDrawingCache()

        bitmap = image.drawingCache
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        mass = stream.toByteArray()

        uploadTask = refToImage.putBytes(mass)
        uploadTask.addOnFailureListener { Log.e(LOG_TAG, it.message) }
                .addOnSuccessListener {
                    OnSuccessListener<UploadTask.TaskSnapshot> {
                    }
                }
    }

    fun downloadImageFromDatabase(presenter: IMyPresenter) {
        val islandRef = storageRef.child(userFirebase.uid)

        islandRef.getBytes(oneMegabyte)
                .addOnFailureListener {
                    OnFailureListener { presenter.onFailureDownloadPhoto(it.message!!) }
                }
                .addOnCompleteListener { array -> presenter.onDownloadPhoto(array.result) }
    }

}