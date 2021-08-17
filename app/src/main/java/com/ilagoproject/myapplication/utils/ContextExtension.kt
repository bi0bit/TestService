package com.ilagoproject.myapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun Context.isOnline() : Boolean{
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}