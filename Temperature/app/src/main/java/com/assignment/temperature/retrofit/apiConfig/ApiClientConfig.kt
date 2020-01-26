package com.zuba.networkUtil.apiConfig

import com.zuba.networkUtil.constants.NetworkConstants
import com.zuba.networkUtil.constants.NetworkConstants.sTIME_OUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClientConfig {
    /**
     * Retrofit Api Implementation
     */
    fun getRetrofit(): Retrofit {
        return if (NetworkConstants.mUseDeveloperServer) {
            initializeRetrofitService(NetworkConstants.sDeveloperNETWORK_URL)
        } else {
            if (NetworkConstants.mUseLocalServer) {
                initializeRetrofitService(NetworkConstants.sBASE_URL)
            } else {
                initializeRetrofitService(NetworkConstants.sBASE_INTERNET_NETWORK_URL)
            }
        }
    }

    /**
     * Initialize Retrofit Api Service
     *
     * @param pBaseUrl - Url for Api
     *
     *
     * Build Connection or handshaking with url and Model
     */
    private fun initializeRetrofitService(pBaseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(pBaseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().apply {
                readTimeout(sTIME_OUT, TimeUnit.SECONDS)
                connectTimeout(sTIME_OUT, TimeUnit.SECONDS)
            }.build())
            .build()
    }
}