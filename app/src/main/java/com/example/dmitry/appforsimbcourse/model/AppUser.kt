package com.example.dmitry.appforsimbcourse.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by dmitry on 15.02.18.
 */

@IgnoreExtraProperties
class AppUser {

    lateinit var email: String
    lateinit var phone: String
    lateinit var name: String

    constructor() {}

    constructor(_email:String, _phone:String, _name:String ) {
        email = _email
        phone = _phone
        name = _name
    }

}