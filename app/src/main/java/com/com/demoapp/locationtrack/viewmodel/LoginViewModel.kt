package com.example.locationtracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.com.demoapp.locationtrack.model.UserLoginmodel
import com.com.demoapp.locationtrack.repro.UserRepository

class LoginViewModel(app:Application,private val repository: UserRepository):AndroidViewModel(app) {


    fun createUser(username: Any, password: Any) {
        repository.createUser(username.toString(), password.toString())
    }

    fun findUser(username: String, password: String): UserLoginmodel? {
        return repository.findUser(username, password)
    }


}

