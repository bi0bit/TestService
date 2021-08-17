package com.ilagoproject.myapplication.model

import android.content.SharedPreferences
import com.ilagoproject.myapplication.apiservice.data.*
import com.ilagoproject.myapplication.model.data.AccountData
import com.ilagoproject.myapplication.model.data.CCPromoData
import com.ilagoproject.myapplication.model.service.AppAssembleService
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import javax.inject.Inject

class AppModel(
    db: Realm,
    service: AppAssembleService
) : BaseModel(db) {

    private var isInit = false

    private var isRememberMe: Boolean = false

    var service = service
        @Inject private set


    val authDataPeanut: AuthorizationData = AuthorizationData()

    val authDataPartner: AuthorizationData = AuthorizationData()

    var accountData: AccountData? = null

    val isAuthDataLoaded: Boolean
        get() = authDataPeanut.login != -1L && authDataPeanut.password.isNotEmpty()

    public val isTokenUpdated: Boolean
        get() = authDataPeanut.token.isNotEmpty() && authDataPartner.token.isNotEmpty()


    override fun init(sharedPreferences: SharedPreferences)
    {
        if(isInit.not()){
            isRememberMe = sharedPreferences.getBoolean("isRemember", false)
            loadSavedAuthData(sharedPreferences)
            isInit = true
        }
    }

    fun setIsRemember(isRemember: Boolean, preferences: SharedPreferences){
        isRememberMe = isRemember
        preferences.edit()
            .putBoolean("isRemember", isRememberMe)
            .apply()
    }

    fun getIsRemember() : Boolean {
        return isRememberMe
    }

    fun setAuthData(login: Long, password: String) {
        authDataPeanut.login = login
        authDataPeanut.password = password
        authDataPartner.login = login
        authDataPartner.password = password
     }

    fun loadSavedAuthData(preferences: SharedPreferences){
        val login = preferences.getLong("login", -1)
        val password = preferences.getString("password", "")

        authDataPeanut.login = login
        authDataPeanut.password = password ?: ""

        authDataPartner.login = login
        authDataPartner.password = password ?: ""
    }

    fun saveAuthData(preferences: SharedPreferences){
        preferences.edit()
            .putLong("login", authDataPeanut.login)
            .putString("password", authDataPeanut.password)
            .apply()
    }

    fun updateTokenFromResponse(response: AuthorizationResponse)
    {
        if(response.result && response is AuthorizationResponseSource){
            when(response.source){
                "Peanut" -> authDataPeanut.token = response.token
                "Partner" -> authDataPartner.token = response.token
            }
        }
    }

    fun signOut(preferences: SharedPreferences){
        preferences.edit().clear().apply()
        setAuthData(-1, "")
        //TODO: Clear cache
    }

    fun loadAccountDataFromCache() {
        accountData = AccountData("*", AccountInfo(), RealmList())
        db.executeTransaction {
            val cachedAccountData = it.where(AccountData::class.java).findFirst()
            if (cachedAccountData != null)
                accountData = cachedAccountData
        }
    }

    fun saveAccountDataToCache(){
        db.beginTransaction()
        db.insert(accountData)
        db.commitTransaction()
    }

    fun clearCache(){
        db.deleteAll()
    }

}