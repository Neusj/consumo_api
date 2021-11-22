package com.example.consumo_api_kt

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.example.consumo_api_kt.RecetaApiClient.RecetaService
import com.example.consumo_api_kt.RecetaApiClient
import retrofit2.Call

class RecetaApiClient private constructor() {
    interface RecetaService {
        @get:GET("recetasApi")
        val recetas: Call<String?>?
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.bakenem.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private var service: RecetaService? = null
    val recetaService: RecetaService?
        get() {
            if (service == null) {
                service = retrofit.create(RecetaService::class.java)
            }
            return service
        }

    companion object {
        val instance = RecetaApiClient()
    }
}