package com.ilagoproject.myapplication.apiservice

import com.ilagoproject.myapplication.apiservice.data.AccountInfo
import com.ilagoproject.myapplication.apiservice.data.AuthorizationData
import com.ilagoproject.myapplication.apiservice.data.AuthorizationRequest
import com.ilagoproject.myapplication.apiservice.data.AuthorizationResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import kotlin.reflect.typeOf

interface Peanut {

    @POST("/api/ClientCabinetBasic/IsAccountCredentialsCorrect")
    fun requestAuthorization(@Body body: AuthorizationRequest) : Observable<AuthorizationResponse>;

    @POST("/api/ClientCabinetBasic/GetAccountInformation")
    fun getAccountInfo(@Body authData: AuthorizationData) : Observable<AccountInfo>;

    @POST("/api/ClientCabinetBasic/GetLastFourNumbersPhone")
    fun getLastNumbersPhone(@Body authData: AuthorizationData) : Observable<String>;

}