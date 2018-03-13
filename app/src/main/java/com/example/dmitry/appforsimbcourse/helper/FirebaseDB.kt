package com.example.dmitry.appforsimbcourse.helper

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.example.dmitry.appforsimbcourse.interfaces.IMyPresenter
import com.example.dmitry.appforsimbcourse.model.AppUser
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

    companion object {
        private const val ONE_MEGABYTE = 1_048_576L
    }

    private val LOG_TAG = "FirebaseDB"

    private val mDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val userFirebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference
    private val refToImage: StorageReference = storageRef.child(userFirebase.uid)
    // не обязатель каждый раз у переменных явно указывать тип
    // статичные значения можно вынести в константы

    fun addNewInDB(user: AppUser) {
        mDatabase.child("users").child(userFirebase.uid).setValue(user)
    }

    fun getUsersInfo(presenter: IMyPresenter) {
        mDatabase.child("users").child(userFirebase.uid)
                .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                dataSnapshot.getValue(AppUser::class.java)?.let {
                                    presenter.updateUI(it)
                                }
                                //!! лучше использовать когда на 100% уверен что там не null. В данном случае он тут был возможен, потому иногда получали NPE
                            }

                            override fun onCancelled(p0: DatabaseError?) = Unit
                        }
                )
    }

    fun saveImageToDatabase(image: ImageView) {
        val bitmap: Bitmap
        val stream = ByteArrayOutputStream()
        val mass: ByteArray //сложно понять что лежит в этих переменных по их названию
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

        islandRef.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener {
                    presenter.onDownloadPhoto(it)
                }
                .addOnFailureListener {
                    OnFailureListener { presenter.onFailureDownloadPhoto(it.message!!) }
                }
        //результат лучше называть либо осознанно либо не называть вообще (возможно в случае с одним параметром)
        //здесь крашится с Object does not exist at location. Возможно изза нового пользователя?
    }

}