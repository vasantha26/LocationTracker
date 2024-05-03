package com.com.demoapp.locationtrack.view

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication :Application() {

    override fun onCreate() {
        super.onCreate()

        // Ready our SDK
        Realm.init(this)

        // Creating our db with custom properties
        val config = RealmConfiguration.Builder()
            .name("location_tracker.realm")
            .schemaVersion(1)
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}