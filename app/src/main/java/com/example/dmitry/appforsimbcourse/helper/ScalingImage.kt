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

        val lastSlash = uri.path.indexOfLast { c -> c == '/'}
        var path = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).absolutePath +
                uri.path.substring(lastSlash)

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path,options)
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

    private fun getRealPath(activity: Activity,uri:Uri):String {
        var cursor:Cursor? = null
        try {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            cursor = activity.contentResolver.query(uri, projection,
                    null,null,null)
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        } finally {
            if(cursor != null) {
                cursor.close()
            }

        }
    }
}