package com.example.dmitry.appforsimbcourse.helper

import android.app.Activity
import android.net.Uri
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

/**
 * Created by dmitry on 01.03.18.
 */
class Cropper {

    companion object {
        fun cropImage(activity: Activity, imageUri:Uri) {
// start cropping activity for pre-acquired image saved on the device
            CropImage.activity(imageUri)
                    .start(activity)

        }
        //можно сделать функцию статической, т.к. в данном случае нам не нужен инстанс этого класса
    }
}

