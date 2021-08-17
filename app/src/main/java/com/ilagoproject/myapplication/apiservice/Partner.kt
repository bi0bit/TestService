package com.ilagoproject.myapplication.apiservice

import com.ilagoproject.myapplication.apiservice.data.AuthorizationRequest
import com.ilagoproject.myapplication.apiservice.data.DataSignal
import com.ilagoproject.myapplication.apiservice.data.Pairs
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*
import java.util.*

interface Partner {

    @POST("/api/Authentication/RequestMoblieCabinetApiToken")
    fun requestAuthorization(@Body body: AuthorizationRequest) : Observable<String>;

    @GET("/clientmobile/GetAnalyticSignals/{login}")
    fun getAnalyticSignals(@Path("login") login: Long,
                           @Header("passkey") token: String,
                           @Query("pairs", encoded = true) pairs : String,
                           @Query("from") from: Long,
                           @Query("to") to: Long,
                           @Query("tradingsystem")tradingSystem : Int = 3) : Observable<List<DataSignal>>

}