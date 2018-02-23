package com.example.dmitry.appforsimbcourse.model

/**
 * Created by dmitry on 15.02.18.
 */
// зачем это?, но вообще data классы лучше писать так:
data class User(
        val login: String,
        val pass: String
)