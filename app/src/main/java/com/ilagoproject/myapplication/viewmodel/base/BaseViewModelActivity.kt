package com.ilagoproject.myapplication.viewmodel.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.ilagoproject.myapplication.App
import com.ilagoproject.myapplication.R
import com.ilagoproject.myapplication.model.BaseModel
import javax.inject.Inject

abstract class BaseViewModelActivity<T> : AppCompatActivity() where T : BaseModel  {

    @Inject
    lateinit var model: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.init(applicationContext.getSharedPreferences(App.APP_PREFERENCE, Context.MODE_PRIVATE))
    }
    override fun onDestroy() {
        model.close()
        super.onDestroy()
    }
}