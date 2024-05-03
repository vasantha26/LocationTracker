package com.com.demoapp.locationtrack.datastore

import com.com.demoapp.locationtrack.model.UserLoginmodel
import io.realm.Realm

class UserDaoImpl  {

     fun createUser(username: String, password: String) {

        val realm = Realm.getDefaultInstance()

        realm.executeTransaction {
            val user = realm.createObject(UserLoginmodel::class.java, System.currentTimeMillis())
            user.username = username
            user.password = password
        }
        realm.close()
    }

     fun getAllUser(username: String, password: String) : UserLoginmodel? {
        val realm = Realm.getDefaultInstance()
        val user = realm.where(UserLoginmodel::class.java)
            .equalTo("username", username)
            .equalTo("password", password)
            .findFirst()
        val userCopy = user?.let { realm.copyFromRealm(it) }

        realm.close()

        return userCopy
    }
}