package com.ilagoproject.myapplication.dagger2.module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class RealmModule {
    @Provides
    fun provideRealmDB(): Realm {
        return Realm.getDefaultInstance()
    }
}