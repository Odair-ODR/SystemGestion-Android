package com.example.website.consulta.Model

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("ruc/{ruc}?token")
    fun obtenerEntidadXRuc(@Path("ruc") ruc: String) : Call<JsonObject>

    @GET("dni/{dni}?token")
    fun obtenerEntidadXDni(@Path("dni") dni: String): Call<JsonObject>
}