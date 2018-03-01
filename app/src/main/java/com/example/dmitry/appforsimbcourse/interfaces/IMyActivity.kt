package com.example.dmitry.appforsimbcourse.interfaces

import android.net.Uri
import com.example.dmitry.appforsimbcourse.model.AppUser

/**
 * Created by dmitry on 24.02.18.
 */
interface IMyActivity {
    fun updateUI(appUser: AppUser) {}
    fun urlPhotoSuccess(url: Uri) {}
}