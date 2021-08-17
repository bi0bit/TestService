package com.ilagoproject.myapplication

import com.google.gson.GsonBuilder
import com.ilagoproject.myapplication.apiservice.*
import com.ilagoproject.myapplication.apiservice.data.*
import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestBody
import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestData
import com.ilagoproject.myapplication.apiservice.request.CCPromoRequestEnvelope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

class ServiceGetInfoUnitTest{

    private lateinit var lockRequest: CountDownLatch

    private lateinit var peanut: Peanut
    private lateinit var partner: Partner
    private lateinit var promo: Promo

    private var accountDataPeanut: AuthorizationData? = null
    private var accountDataPartner: AuthorizationData? = null

    private val authData: AuthorizationRequest = AuthorizationRequest(20234561, "ladevi31");


    @Before
    fun authorizationService(){
        val lockPeanut = CountDownLatch(1)
        val lockPartner = CountDownLatch(1)
        val gson = GsonBuilder()
                .setLenient()
                .create()
        lockRequest = CountDownLatch(1)
        peanut = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://peanut.ifxdb.com/")
            .build()
            .create(Peanut::class.java)
        partner = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://client-api.contentdatapro.com/")
            .build()
            .create(Partner::class.java)
        val strategy = AnnotationStrategy()
        val serializer = Persister(strategy)
        val interceptor = HttpLoggingInterceptor {
            println(it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build()
        promo = Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                .baseUrl("https://api-forexcopy.contentdatapro.com/")
                .client(client)
                .build()
                .create(Promo::class.java)
        peanut.requestAuthorization(authData).subscribe(
            //OnNext
            {   response ->
                    if(response != null)
                        accountDataPeanut = AuthorizationData(authData.login, authData.password, response.token)
                    lockPeanut.countDown()
            },
            //OnError
            {   _ ->
                    lockPeanut.countDown()
            }
        )
        partner.requestAuthorization(authData).subscribe(
            //OnNext
            {   response ->
                    if(response != null)
                        accountDataPartner = AuthorizationData(authData.login, authData.password,
                            response
                        )
                    lockPartner.countDown()
            },
            //OnError
            {    t ->
                    lockPartner.countDown()
            }
        )
        lockPeanut.await()
        lockPartner.await()
    }

    @Test
    fun getAccountInfo(){
        Assert.assertNotNull("AuthorizationFail $accountDataPeanut", accountDataPeanut)
        var accountInfo: AccountInfo? = null;
        peanut.getAccountInfo(accountDataPeanut!!).subscribe(
            //OnNext
            {   response ->
                    println(" === result ===")
                    print("account info - $response")
                    accountInfo = response
                    lockRequest.countDown()
            },
            //OnError
            { _ ->
                lockRequest.countDown()
            }
        )
        lockRequest.await()
        Assert.assertNotNull(accountInfo)
    }

    @Test
    fun getLastNumbersPhone(){
        Assert.assertNotNull("AuthorizationFail $accountDataPeanut", accountDataPeanut)
        var number:String? = null
        peanut.getLastNumbersPhone(accountDataPeanut!!).subscribe(
            //OnNext
            {   response ->

                    println(" === result ===")
                    print("lastNumbers - $response")
                    number = response
                    lockRequest.countDown()
            },
            //OnError
            {    t ->
                    println(t.message)
                    lockRequest.countDown()
            }
        )
        lockRequest.await()
        Assert.assertNotNull(number)
    }

    @Test
    fun getAnalyticSignals(){
        Assert.assertNotNull("AuthorizationFail $accountDataPartner", accountDataPartner)
        var analyticSignals: List<DataSignal>? = null
        val from = SimpleDateFormat("dd.MM.yyyy").parse("30.07.2021").time / 1000
        val to = SimpleDateFormat("dd.MM.yyyy").parse("09.08.2021").time / 1000
        accountDataPartner?.let {
            val pairs = EnumSet.of(Pairs.EURUSD, Pairs.USDJPY).stream()
                .map(Any::toString)
                .collect(Collectors.joining(","))
            val requestAnalyticSignals = partner.getAnalyticSignals(it.login, it.token, pairs = pairs, from = from, to = to)
            requestAnalyticSignals.subscribe(
                //OnNext
                {   response ->
                            println(" === result ===")
                            print("lastNumbers - $response")
                            analyticSignals = response
                            lockRequest.countDown()
                },
                //OnError
                {   t->
                            println(t.message)
                            lockRequest.countDown()
                }
            )
            lockRequest.await(5, TimeUnit.SECONDS)
        }
        Assert.assertNotNull(analyticSignals)
    }

    @Test
    fun getCCPromo(){
        val lang = CCPromoRequestData("en")
        val body = CCPromoRequestBody(lang)
        val envelope = CCPromoRequestEnvelope(body)
        var cCPromo:String? = null
        promo.getCCPromo(envelope)
                .subscribe(
                    //OnNext
                    {   response ->
                            val body = response?.body
                            println(" === result ===")
                            print("lastNumbers - $body")
                            cCPromo = body?.data?.result
                            lockRequest.countDown()
                    },
                    //OnError
                    {   t ->
                            println(t.message)
                            lockRequest.countDown()
                    }
                )
        lockRequest.await(5, TimeUnit.SECONDS)
        Assert.assertNotNull(cCPromo)
    }



}