package com.com.demoapp.locationtrack.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserLoginmodel (@PrimaryKey
                           var id: Long = 0,
                           var username: String = "",
                           var password: String = ""):RealmObject()

