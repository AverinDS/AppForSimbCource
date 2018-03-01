package com.example.dmitry.appforsimbcourse.helper

import android.app.Activity
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.InputStream


/**
 * Created by dmitry on 28.02.18.
 */
class ScalingImage {

    fun scaleImage(activity: Activity, uri: Uri, reqWidth: Int, reqHeight: Int):Bitmap {
        //set options
        val options = BitmapFactory.Options()
        var imageStream = activity.contentResolver.openInputStream(uri)

        options.inJustDecodeBounds = true

        BitmapFactory.decodeStream(imageStream,null, options)

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false

        //open stream again, because after decoding stream it has changed
        imageStream = activity.contentResolver.openInputStream(uri)

        return BitmapFactory.decodeStream(imageStream, null,options)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}