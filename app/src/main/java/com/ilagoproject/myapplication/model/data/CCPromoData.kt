package com.ilagoproject.myapplication.model.data

import io.realm.RealmObject

open class CCPromoData (
     var image : String = "",
     var text : String = "",
     var link : String = "",
     var buttonText : String = "",
     var buttonColor : String = "",
     var euroAvailable : Boolean = false,
     var dieDate : Int = -1
) : RealmObject()
