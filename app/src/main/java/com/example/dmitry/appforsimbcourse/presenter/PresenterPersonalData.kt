package com.example.dmitry.appforsimbcourse.presenter

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.support.v4.content.ContextCompat
import android.widget.EditText
import com.example.dmitry.appforsimbcourse.helper.FirebaseDB
import com.example.dmitry.appforsimbcourse.model.AppUser
import com.example.dmitry.appforsimbcourse.interfaces.IMyActivity
import com.example.dmitry.appforsimbcourse.interfaces.IMyPresenter
import com.example.dmitry.appforsimbcourse.view.ProfileActivity
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.example.dmitry.appforsimbcourse.helper.Permissons
import ru.tinkoff.decoro.BuildConfig
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by dmitry on 23.02.18.
 */
class PresenterPersonalData(_activity: IMyActivity) : IMyPresenter {
    private val activity: IMyActivity = _activity

    private val LOG_TAG: String = "PresenterPersonalData"

    private val dbHelper = FirebaseDB()


    fun emailIsValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun setMaskPhone(editText: EditText) {
        val formatWatcher: FormatWatcher = MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER))
        formatWatcher.installOn(editText)
    }

    fun updateDataServer(email: String, phone: String, name: String) {
        dbHelper.addNewInDB(AppUser(email, phone, name))
    }

    fun getUsersInfo() {
        dbHelper.getUsersInfo(this)
    }

    override fun updateUI(appUser: AppUser) {
        activity.updateUI(appUser)
    }

    fun startProfileActivity(parent: Activity) {
        val intent = Intent(parent, ProfileActivity::class.java)
        ContextCompat.startActivity(parent, intent, null)
    }

    fun getIntentOnImageFromGallery():Intent {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.type = ("image/*")
        return intent
    }

    fun getPhotoFromCamera(activity: Activity):Intent {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)



        val photofile = createImageFile(activity)
        val photoUri = FileProvider.getUriForFile(activity,
                "com.example.dmitry.appforsimbcourse.fileprovider", photofile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        return intent
    }

    fun getCameraPermission(activity:Activity, codeCamera:Int) {
        val perm = Permissons()
        perm.requestCameraPermission(activity,codeCamera)
    }


    @Throws(IOException::class)
    private fun createImageFile(a:Activity): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = a.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        var mCurrentPhotoPath = image.absolutePath
        return image
    }


}