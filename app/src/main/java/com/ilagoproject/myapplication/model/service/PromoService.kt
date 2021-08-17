package com.ilagoproject.myapplication.model.service

import com.ilagoproject.myapplication.apiservice.Promo
import com.ilagoproject.myapplication.apiservice.data.AccountInfo
import com.ilagoproject.myapplication.apiservice.data.AuthorizationData
import com.ilagoproject.myapplication.apiservice.data.AuthorizationRequest
import com.ilagoproject.myapplication.apiservice.data.AuthorizationResponse
import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestBody
import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestData
import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestEnvelope
import com.ilagoproject.myapplication.apiservice.response.CCPromoResponseEnvelope
import com.ilagoproject.myapplication.model.data.AccountData
import io.reactivex.rxjava3.core.Observable
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class PromoService constructor(private val promoApi: Promo) : Service {
    override fun signIn(authRequest: AuthorizationRequest): Observable<AuthorizationResponse> {
        return Observable.empty()
    }

    override fun getAccountInfo(authData: AuthorizationData): Observable<AccountInfo> {
        return Observable.empty()
    }

    override fun getAccountData(
        authData: AuthorizationData,
        requestData: HashMap<String, *>
    ): Observable<AccountData> {
        return Observable.empty()
    }

    override fun getAdditionalInfo(): Observable<CCPromoResponseEnvelope> {
        val data = CCPromoRequestData(Locale.getDefault().language)
        val body = CCPromoRequestBody(data)
        val envelopeRequest = CCPromoRequestEnvelope(body)
        return promoApi.getCCPromo(envelopeRequest)
    }
}