package com.com.demoapp.locationtrack.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.com.demoapp.locationtrack.datastore.UserDaoImpl
import com.com.demoapp.locationtrack.repro.UserRepository
import com.com.demoapp.locationtrack.viewmodel.ViewModelFactory
import com.demoapp.locationtrack.R
import com.demoapp.locationtrack.databinding.ActivityMainBinding
import com.example.locationtracker.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: LoginViewModel

    //changed 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)

        setdatabase()
    }

    private fun setdatabase() {
        val repository=UserRepository(UserDaoImpl())
        val factory= ViewModelFactory(application,repository)
        mainViewModel=ViewModelProvider(this,factory).get(LoginViewModel::class.java)
    }

}