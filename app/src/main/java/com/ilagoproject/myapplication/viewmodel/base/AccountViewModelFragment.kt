package com.ilagoproject.myapplication.viewmodel.base

import android.content.Context
import com.ilagoproject.myapplication.App
import com.ilagoproject.myapplication.R

open class AccountViewModelFragment : AppViewModelFragment(), ISignOut{
    override fun signOut() {
//        model.clearCache()
        model.signOut(context!!.getSharedPreferences(App.APP_PREFERENCE, Context.MODE_PRIVATE))
        goToPage(R.id.authorizationFragment)
    }


}