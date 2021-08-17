package com.ilagoproject.myapplication

import android.app.Application
import android.content.SharedPreferences
import com.ilagoproject.myapplication.dagger2.component.DaggerModelComponent
import com.ilagoproject.myapplication.dagger2.component.ModelComponent
import io.realm.Realm

class App : Application() {
    companion object{
        lateinit var modelComponent: ModelComponent
        const val APP_PREFERENCE = "setting"
        const val URL_BASE_PEANUT = "https://peanut.ifxdb.com/"
        const val URL_BASE_PARTNER = "https://client-api.contentdatapro.com/"
        const val URL_BASE_PROMO = "https://api-forexcopy.contentdatapro.com/"
    }

    override fun onCreate() {
        Realm.init(this)
        modelComponent = DaggerModelComponent.create()
        super.onCreate()
    }
}