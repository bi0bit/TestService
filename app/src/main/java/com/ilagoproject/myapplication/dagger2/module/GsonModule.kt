package com.ilagoproject.myapplication.dagger2.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory

@Module
class GsonModule {
    @Provides
    fun provideGsonFactory() : GsonConverterFactory
    {
        return GsonConverterFactory.create(getGson())
    }
    @Provides
    fun provideGson() : Gson
    {
        return getGson()
    }

    fun getGson() : Gson
    {
        return GsonBuilder().setLenient().create()
    }
}