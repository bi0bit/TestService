package com.ilagoproject.myapplication.model.service

import com.ilagoproject.myapplication.apiservice.SourceResponse
import com.ilagoproject.myapplication.apiservice.data.AccountInfo
import com.ilagoproject.myapplication.apiservice.data.AuthorizationData
import com.ilagoproject.myapplication.apiservice.data.AuthorizationRequest
import com.ilagoproject.myapplication.apiservice.data.AuthorizationResponse
import com.ilagoproject.myapplication.apiservice.response.CCPromoResponseEnvelope
import com.ilagoproject.myapplication.model.data.AccountData
import io.reactivex.rxjava3.core.Observable

interface Service {
    fun signIn(authRequest: AuthorizationRequest): Observable<AuthorizationResponse>
    fun getAccountInfo(authData: AuthorizationData): Observable<AccountInfo>
    fun getAccountData(authData: AuthorizationData, requestData: HashMap<String, *>): Observable<AccountData>
    fun getAdditionalInfo(): Observable<CCPromoResponseEnvelope>
}