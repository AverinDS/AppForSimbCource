package com.example.dmitry.appforsimbcourse.presenter

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.dmitry.appforsimbcourse.helper.FirebaseDB
import com.example.dmitry.appforsimbcourse.interfaces.IMyActivity
import com.example.dmitry.appforsimbcourse.interfaces.IMyPresenter
import com.example.dmitry.appforsimbcourse.model.AppUser
import com.example.dmitry.appforsimbcourse.view.PersonalDataActivity

/**
 * Created by dmitry on 24.02.18.
 */
class PresenterProfile(_activity: IMyActivity) : IMyPresenter {

    private val dbHelper = FirebaseDB()
    private val activity = _activity

    fun getUsersInfo() {
        dbHelper.getUsersInfo(this)
    }

    override fun updateUI(appUser: AppUser) {
        activity.updateUI(appUser)
    }

    fun downloadPhoto() {
        dbHelper.downloadImageFromDatabase(this)
    }

    override fun onDownloadPhoto(bytes: ByteArray) {
        activity.onDownloadPhoto(BitmapFactory.decodeByteArray(bytes, 0,
                bytes.size))
    }

    override fun onFailureDownloadPhoto(message: String) {
        activity.onFailureDownloadPhoto(message)
    }

    fun startPersonalDataActivity(parent: Activity) {
        val intent = Intent(parent, PersonalDataActivity::class.java)
        ContextCompat.startActivity(parent, intent, null)
    }
}