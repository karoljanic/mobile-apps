package com.example.newtonapi.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewtonFetch {
    fun getAnswer(operation: NewtonService.Operation, data:String, onSuccess: (result: String) -> Unit) {
        val url = "${operation.baseUrl}$data/"

        val retrofitBuilder =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url).build().create(NewtonService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val retrofitData = retrofitBuilder.getAnswer(url)
            if (retrofitData != null) {
                retrofitData.body()?.let { onSuccess(it.result) }
            }
        }
    }
}