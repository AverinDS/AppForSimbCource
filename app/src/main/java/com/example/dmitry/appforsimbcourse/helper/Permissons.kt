package com.example.dmitry.appforsimbcourse.helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast

/**
 * Created by dmitry on 26.02.18.
 */
class Permissons {
    fun requestCameraPermission(activity: Activity, codeCamera:Int) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.CAMERA)) {
                Toast.makeText(activity,"Камера нужна для того, чтобы сделать аватар", //строки лучше выносить в ресурсы strings.xml
                        Toast.LENGTH_SHORT).show()
                //функцию по показу тоста лучше вынести в отдельный класс типа ViewUtils
            } else {
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.CAMERA),
                        codeCamera)
                //этот код дублируется, что тоже не очень хорошо. Читай про принципы SOLID, DRY, KISS
            }
        } else {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.CAMERA),
                    codeCamera)
            // Permission has already been granted
        }
    }
}