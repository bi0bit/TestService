package com.ilagoproject.myapplication.apiservice.data

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class DataSignal (
    @SerializedName("Id") var id : Int = -1,
    @SerializedName("ActualTime") var actualTime : Long = -1,
    @SerializedName("Comment") var comment : String = "",
    @SerializedName("Pair") var pair : String = "",
    @SerializedName("Cmd") var cmd : Int = -1,
    @SerializedName("TradingSystem") var tradingSystem : Int = -1,
    @SerializedName("Period") var period : String = "",
    @SerializedName("Price") var price : Double = 0.0,
    @SerializedName("Sl") var sl : Double = 0.0,
    @SerializedName("Tp") var tp : Double = 0.0
) : RealmObject()
