package com.ilagoproject.myapplication

import android.os.Bundle
import com.ilagoproject.myapplication.dagger2.component.*
import com.ilagoproject.myapplication.dagger2.module.*
import com.ilagoproject.myapplication.model.AppModel
import com.ilagoproject.myapplication.viewmodel.base.AppViewModelActivity
import com.ilagoproject.myapplication.viewmodel.base.BaseViewModelActivity

class MainActivity : AppViewModelActivity() {

    init
    {
        App.modelComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}