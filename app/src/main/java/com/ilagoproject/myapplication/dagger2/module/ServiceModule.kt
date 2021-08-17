package com.ilagoproject.myapplication.dagger2.module

import com.ilagoproject.myapplication.App
import com.ilagoproject.myapplication.apiservice.Partner
import com.ilagoproject.myapplication.apiservice.Peanut
import com.ilagoproject.myapplication.apiservice.Promo
import com.ilagoproject.myapplication.model.service.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ServiceModule {

    @Provides
    fun providePeanutApi(gsonFactory : GsonConverterFactory, client: OkHttpClient) : PeanutService {
        val api = Retrofit.Builder()
            .addConverterFactory(gsonFactory)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .baseUrl(App.URL_BASE_PEANUT)
            .build()
            .create(Peanut::class.java)
        return PeanutService(api)
    }

    @Provides
    fun providePartnerApi(gsonFactory : GsonConverterFactory, client: OkHttpClient) : PartnerService {
        val api = Retrofit.Builder()
            .addConverterFactory(gsonFactory)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .baseUrl(App.URL_BASE_PARTNER)
            .build()
            .create(Partner::class.java)
        return PartnerService(api)
    }

    @Provides
    fun providePromoApi(client: OkHttpClient) : PromoService {
        val strategy = AnnotationStrategy()
        val serializer = Persister(strategy)
        val api = Retrofit.Builder()
            .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .baseUrl(App.URL_BASE_PROMO)
            .build()
            .create(Promo::class.java)
        return PromoService(api)
    }

    @Provides
    fun provideAppService(peanutService: PeanutService, partnerService: PartnerService, promoService: PromoService) : AppAssembleService {
        return AppAssembleService(peanutService, partnerService, promoService)
    }


}