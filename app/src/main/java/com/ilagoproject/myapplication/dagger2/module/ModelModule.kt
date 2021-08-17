package com.ilagoproject.myapplication.dagger2.module

import com.ilagoproject.myapplication.dagger2.AssembleService
import com.ilagoproject.myapplication.dagger2.scope.AppScope
import com.ilagoproject.myapplication.model.AppModel
import com.ilagoproject.myapplication.model.service.AppAssembleService
import com.ilagoproject.myapplication.model.service.Service
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class ModelModule {
    @Provides
    @AppScope
    fun provideModelService(db: Realm, service: AppAssembleService) : AppModel {
        return AppModel(db, service)
    }
}