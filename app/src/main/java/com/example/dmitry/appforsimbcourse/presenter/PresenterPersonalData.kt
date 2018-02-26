package com.example.dmitry.appforsimbcourse.presenter

import android.app.Activity
import android.content.Intent
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

    fun getPhotoFromGallery() {

    }

    fun getPhotoFromCamera() {

    }

}