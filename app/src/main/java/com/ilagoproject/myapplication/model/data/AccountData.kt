package com.ilagoproject.myapplication.model.data

import com.ilagoproject.myapplication.apiservice.data.AccountInfo
import com.ilagoproject.myapplication.apiservice.data.DataSignal
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmModule


open class AccountData(
    var accountNumber:String? = "",
    var accountInfo: AccountInfo? = null,
    var dataSignals: RealmList<DataSignal>? = RealmList()
) : RealmObject()