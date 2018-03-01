package com.example.dmitry.appforsimbcourse.helper

import android.app.Activity
import android.net.Uri
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

/**
 * Created by dmitry on 01.03.18.
 */
class Cropper {
    fun cropImage(activity: Activity, imageUri:Uri) {
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(activity)

// start cropping activity for pre-acquired image saved on the device
        CropImage.activity(imageUri)
                .start(activity)

    }
}

