package com.ilagoproject.myapplication.model

import android.content.Context
import android.content.SharedPreferences
import io.realm.Realm
import javax.inject.Inject

abstract class BaseModel(db: Realm) {
    var db: Realm = db
         @Inject protected set

    abstract fun init(sharedPreferences: SharedPreferences)
    open fun close(){
        db.close()
    }
}
