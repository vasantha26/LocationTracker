package com.com.demoapp.locationtrack.repro

import com.com.demoapp.locationtrack.datastore.UserDaoImpl
import com.com.demoapp.locationtrack.model.UserLoginmodel

class UserRepository (private val userDao: UserDaoImpl) {

    fun createUser(username: String, password: String) {
        userDao.createUser(username, password)
    }

    fun findUser(username: String, password: String): UserLoginmodel? {
        return userDao.getAllUser(username, password)
    }
}