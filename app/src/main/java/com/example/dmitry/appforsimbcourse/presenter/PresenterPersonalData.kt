package com.example.dmitry.appforsimbcourse.presenter

import android.widget.EditText
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

/**
 * Created by dmitry on 23.02.18.
 */
class PresenterPersonalData {
    private val LOG_TAG:String = "PresenterPersonalData"

    fun emailIsValid(email:String):Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun setMaskPhone(editText:EditText) {
        val formatWatcher:FormatWatcher = MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER))
        formatWatcher.installOn(editText)
    }
}