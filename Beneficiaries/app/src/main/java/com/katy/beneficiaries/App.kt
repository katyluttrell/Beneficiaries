package com.katy.beneficiaries

import android.app.Application
import com.katy.beneficiaries.di.AppComponent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponent.initialize()
    }
}
