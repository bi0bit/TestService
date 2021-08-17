package com.ilagoproject.myapplication.apiservice

import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestEnvelope
import com.ilagoproject.myapplication.apiservice.response.CCPromoResponseEnvelope
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Promo {
    @Headers("Content-Type: text/xml",
        "Accept-Charset: utf-8",
        "SOAPAction: http://tempuri.org/CabinetMicroService/GetCCPromo")
    @POST("/Services/CabinetMicroService.svc")
    fun getCCPromo(@Body body : CCPromoRequestEnvelope) : Observable<CCPromoResponseEnvelope>

}

