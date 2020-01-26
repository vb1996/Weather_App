package com.assignment.temperature.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.temperature.datamodel.Response
import com.assignment.temperature.retrofit.apis.Apis
import com.google.gson.Gson
import com.zuba.networkUtil.apiConfig.ApiClientConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    var mResponse = MutableLiveData<Response>()

    fun getLiveDataResponse(): MutableLiveData<Response> {
        return mResponse
    }

    fun downloadWeatherInfo() {
        val gson = Gson()
        val apiClientConfig = ApiClientConfig()
        val mObservable =
            apiClientConfig.getRetrofit().create<Apis>(Apis::class.java)
                .downloadWeatherInfor("3534")
        mObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(pResponse: Response) {
        this.getLiveDataResponse().postValue(pResponse)
    }

    private fun handleError(error: Throwable) {
    }
}
