package com.ilagoproject.myapplication.viewmodel.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ilagoproject.myapplication.App
import com.ilagoproject.myapplication.model.BaseModel
import javax.inject.Inject

abstract class BaseViewModelFragment<T> : Fragment() where T : BaseModel {

    @Inject
    lateinit var model: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.init(context!!.getSharedPreferences(App.APP_PREFERENCE, Context.MODE_PRIVATE))
    }

    override fun onDestroy() {
        model.close()
        super.onDestroy()
    }

    fun goToPage(id: Int){
        findNavController().navigate(id)
    }
}