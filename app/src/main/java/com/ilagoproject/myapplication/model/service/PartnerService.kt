package com.ilagoproject.myapplication.model.service

import android.util.Log
import com.ilagoproject.myapplication.apiservice.Partner
import com.ilagoproject.myapplication.apiservice.SourceResponse
import com.ilagoproject.myapplication.apiservice.data.*
import com.ilagoproject.myapplication.apiservice.response.CCPromoResponseEnvelope
import com.ilagoproject.myapplication.model.data.AccountData
import io.reactivex.rxjava3.core.Observable
import io.realm.RealmList
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class PartnerService constructor(private val partnerApi: Partner) : Service {

    override fun signIn(authRequest: AuthorizationRequest): Observable<AuthorizationResponse> {
        return partnerApi.requestAuthorization(authRequest)
            .flatMap { password ->
                Observable.just(AuthorizationResponse(ExtensionData(), password.isNotEmpty(), password))
            }
            .map {
                AuthorizationResponseSource("Partner", it)
            }
    }

    override fun getAccountInfo(authData: AuthorizationData): Observable<AccountInfo> {
        return Observable.empty()
    }

    override fun getAccountData(authData: AuthorizationData, requestData: HashMap<String, *>): Observable<AccountData> {
        val valuePairs = requestData.getValue("pairs") as String
        val from = requestData.getValue("from") as Long
        val to = requestData.getValue("to") as Long
        return partnerApi.getAnalyticSignals(authData.login, authData.token, valuePairs, from, to)
            .flatMap {
                val dataSignals = RealmList<DataSignal>()
                dataSignals.addAll(it)
                Observable.just(AccountData(null, null, dataSignals))
            }
    }

    override fun getAdditionalInfo(): Observable<CCPromoResponseEnvelope> {
        return Observable.empty()
    }
}