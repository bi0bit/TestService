package com.ilagoproject.myapplication

import com.ilagoproject.myapplication.apiservice.*
import com.ilagoproject.myapplication.apiservice.data.AuthorizationRequest
import com.ilagoproject.myapplication.apiservice.data.AuthorizationResponse
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep

class ServiceAuthUnitTest {

    private lateinit var peanut: Peanut
    private lateinit var partner: Partner

    @Before
    fun retrofitInit(){
        peanut  =   Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://peanut.ifxdb.com/")
            .build()
            .create(Peanut::class.java)
        partner = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://client-api.contentdatapro.com/")
            .build()
            .create(Partner::class.java)
    }

    @Test
    fun authorizationPeanut(){
        val auth = peanut.requestAuthorization(AuthorizationRequest(20234561, "ladevi31"));
        auth.enqueue(object : Callback<AuthorizationResponse>{
            override fun onResponse(call: Call<AuthorizationResponse>?, response: Response<AuthorizationResponse>?)
            {
                if(response == null) return
                println("request result:" + response.code())
                println(" === header response data ===")
                println(response.headers())
                println(" === error response ===")
                println(response.message())
                println(response.errorBody()?.string())
                val body = response.body() ?: return
                if(body.result)
                    print("token - " + body.token)
            }

            override fun onFailure(call: Call<AuthorizationResponse>?, t: Throwable?) {
                assert(false)
            }

        })
        sleep(2000)
    }

    @Test
    fun authorizationPartner(){
        val auth = partner.requestAuthorization(AuthorizationRequest(20234561, "ladevi31"));
        auth.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>?, response: Response<String>?)
            {
                if(response == null) return
                println("request result:" + response.code())
                println(" === header response data ===")
                println(response.headers())
                println(" === error response ===")
                println(response.message())
                println(response.errorBody()?.string())
                val body = response.body() ?: return
                print("token - $body")
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                assert(false)
            }

        })
        sleep(2000)
    }



}