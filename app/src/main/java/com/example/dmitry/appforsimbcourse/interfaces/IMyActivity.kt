package com.example.dmitry.appforsimbcourse.interfaces

import android.graphics.Bitmap
import android.net.Uri
import com.example.dmitry.appforsimbcourse.model.AppUser

/**
 * Created by dmitry on 24.02.18.
 */
interface IMyActivity {
    fun updateUI(appUser: AppUser) {}
    fun onDownloadPhoto(bitmap: Bitmap) {}
    fun onFailureDownloadPhoto(message: String) {}
}