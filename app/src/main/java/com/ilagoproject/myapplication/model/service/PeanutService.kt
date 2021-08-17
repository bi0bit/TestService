package com.ilagoproject.myapplication.model.service

import com.ilagoproject.myapplication.apiservice.Peanut
import com.ilagoproject.myapplication.apiservice.SourceResponse
import com.ilagoproject.myapplication.apiservice.data.*
import com.ilagoproject.myapplication.apiservice.response.CCPromoResponseEnvelope
import com.ilagoproject.myapplication.model.data.AccountData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function
import java.util.*
import javax.inject.Inject
import javax.xml.transform.Source
import kotlin.collections.HashMap
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.typeOf

class PeanutService constructor(private val peanutApi: Peanut) : Service {

    override fun signIn(authRequest: AuthorizationRequest): Observable<AuthorizationResponse> {
        return peanutApi.requestAuthorization(authRequest)
            .map{
                AuthorizationResponseSource("Peanut", it)
            }
    }

    override fun getAccountInfo(authData: AuthorizationData): Observable<AccountInfo> {
        return peanutApi.getAccountInfo(authData)
    }

    override fun getAccountData(
        authData: AuthorizationData,
        requestData: HashMap<String, *>
    ): Observable<AccountData> {
        return peanutApi.getLastNumbersPhone(authData).flatMap {
            Observable.just(AccountData(it, null, null))
        }
    }

    override fun getAdditionalInfo(): Observable<CCPromoResponseEnvelope> {
        return Observable.empty()
    }
}