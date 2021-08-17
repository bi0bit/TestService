package com.ilagoproject.myapplication.apiservice.data

import io.realm.RealmObject
import io.realm.annotations.Ignore

open class AccountInfo (
    @Ignore
    var extensionData : ExtensionData = ExtensionData(),
    var address : String = "",
    var balance : Double = 0.0,
    var city : String = "",
    var country : String = "",
    var currency : Int = -1,
    var currentTradesCount : Int = -1,
    var currentTradesVolume : Int = -1,
    var equity : Double = 0.0,
    var freeMargin : Double = 0.0,
    var isAnyOpenTrades : Boolean = false,
    var isSwapFree : Boolean = false,
    var leverage : Int = -1,
    var name : String = "",
    var phone : String = "",
    var totalTradesCount : Int = -1,
    var totalTradesVolume : Int = -1,
    var type : Int = -1,
    var verificationLevel : Int = -1,
    var zipCode : String = ""
) : RealmObject()
