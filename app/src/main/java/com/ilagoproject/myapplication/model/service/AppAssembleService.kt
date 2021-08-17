package com.ilagoproject.myapplication.model.service

import android.util.Log
import com.ilagoproject.myapplication.apiservice.data.*
import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestBody
import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestData
import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestEnvelope
import com.ilagoproject.myapplication.apiservice.response.CCPromoResponseEnvelope
import com.ilagoproject.myapplication.model.data.AccountData
import com.ilagoproject.myapplication.model.data.CCPromoData
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import kotlin.collections.HashMap

class AppAssembleService constructor(
    peanut: PeanutService,
    partner: PartnerService,
    ccPromo: PromoService
) : Service {

    var ccPromo = ccPromo
        @Inject private set
    var partner = partner
        @Inject private set
    var peanut = peanut
        @Inject private set

    override fun signIn(authRequest: AuthorizationRequest): Observable<AuthorizationResponse> {
        val auth1 = peanut.signIn(authRequest)
        val auth2 = partner.signIn(authRequest)
        return auth1.mergeWith(auth2)
    }

    override fun getAccountInfo(authData: AuthorizationData): Observable<AccountInfo> {
        return peanut.getAccountInfo(authData)
    }

    override fun getAccountData(
        authData: AuthorizationData,
        requestData: HashMap<String, *>
    ): Observable<AccountData> {
        return Observable.error(NotImplementedError())
    }

    fun getAccountData(
        authDataPeanut: AuthorizationData,
        authDataPartner: AuthorizationData,
        requestData: HashMap<String, *>
    ): Observable<AccountData> {
        val getAccInfo = getAccountInfo(authDataPeanut).flatMap {
            Observable.just(AccountData(null, it, null))
        }
        val getData = peanut.getAccountData(authDataPeanut, requestData)
        val getData2 = partner.getAccountData(authDataPartner, requestData)
        return getAccInfo
            .zipWith(getData){ accountData: AccountData, accountData1: AccountData ->
                accountData.accountNumber = accountData1.accountNumber
                accountData
            }
            .zipWith(getData2){ accountData: AccountData, accountData1: AccountData ->
                accountData.dataSignals = accountData1.dataSignals
                accountData
            }
    }

    override fun getAdditionalInfo(): Observable<CCPromoResponseEnvelope> {
        return ccPromo.getAdditionalInfo()
    }

}