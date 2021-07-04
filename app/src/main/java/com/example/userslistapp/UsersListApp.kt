package com.example.userslistapp

import android.app.Application
import com.example.userslistapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class UsersListApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@UsersListApp)
            modules(appModule)
        }
    }
}