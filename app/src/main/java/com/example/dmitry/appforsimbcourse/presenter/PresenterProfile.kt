package com.example.dmitry.appforsimbcourse.presenter

import android.net.Uri
import com.example.dmitry.appforsimbcourse.helper.FirebaseDB
import com.example.dmitry.appforsimbcourse.interfaces.IMyActivity
import com.example.dmitry.appforsimbcourse.interfaces.IMyPresenter
import com.example.dmitry.appforsimbcourse.model.AppUser

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


    override fun onDownloadPhoto(bytes: ByteArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailureDownloadPhoto(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}