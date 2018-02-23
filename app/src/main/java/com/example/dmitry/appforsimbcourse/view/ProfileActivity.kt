package com.example.dmitry.appforsimbcourse.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.dmitry.appforsimbcourse.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        actProfileName.text = ("Добро пожаловать, " + intent.getStringExtra("email") +
                "! Тут потом будет окно профиля ")
        // тексты лучше помещать в ресурсы и биндить ко вьюхам через layout, а если нужны динамически включения - использовать placeholders
        // https://developer.android.com/guide/topics/resources/string-resource.html
        //дотуп к вьюхам можно получать прямо через их id, если применен kotlin-extensions plugin
    }
}
