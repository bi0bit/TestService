package com.ilagoproject.myapplication.dagger2.component


import com.google.gson.Gson
import com.ilagoproject.myapplication.dagger2.module.*
import com.ilagoproject.myapplication.dagger2.scope.AppScope
import com.ilagoproject.myapplication.model.BaseModel
import com.ilagoproject.myapplication.viewmodel.base.AppViewModelActivity
import com.ilagoproject.myapplication.viewmodel.base.AppViewModelFragment
import com.ilagoproject.myapplication.viewmodel.base.BaseViewModelActivity
import com.ilagoproject.myapplication.viewmodel.base.BaseViewModelFragment
import dagger.Component
import javax.inject.Singleton

@AppScope
@Component(modules = [ModelModule::class, RealmModule::class, ServiceModule::class, OkHttpModule::class, GsonModule::class])
interface ModelComponent
{
        fun inject(activity: AppViewModelActivity)
        fun inject(fragment: AppViewModelFragment)

        fun getGson(): Gson
}