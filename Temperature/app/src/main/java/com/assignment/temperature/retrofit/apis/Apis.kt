package com.assignment.temperature.retrofit.apis

import com.assignment.temperature.datamodel.Response
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Apis {
    @GET("api/location/{conversationId}/")
    fun downloadWeatherInfor(@Path("conversationId") conversationId: String): Observable<Response>
}