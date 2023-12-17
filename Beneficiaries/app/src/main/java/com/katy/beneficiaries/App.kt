package com.katy.beneficiaries

import android.app.Application
import com.katy.beneficiaries.di.AppComponent
import com.katy.beneficiaries.di.AppModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponent.initialize(AppModule())
    }
}
