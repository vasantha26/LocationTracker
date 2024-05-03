package com.com.demoapp.locationtrack.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.com.demoapp.locationtrack.repro.UserRepository
import com.example.locationtracker.viewmodel.LoginViewModel

class ViewModelFactory(private var app:Application,private var repository: UserRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(app,repository) as T
    }

}