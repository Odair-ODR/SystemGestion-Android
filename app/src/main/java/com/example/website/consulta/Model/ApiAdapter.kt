package com.example.website.consulta.Model

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAdapter {
    val apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6IkJlbGx5d29vZHlAaG90bWFpbC5jb20ifQ.Wf8CGBfUJ7kq4gVzKK4v7xFMkPdFvVzqILTDHhVa34c"
    val urlApi = "https://dniruc.apisperu.com/api/v1/"

    fun getClientService(): ApiService {
        val authInterceptor = Interceptor { chain ->
            val url = chain.request().url().newBuilder()
                    .addQueryParameter("token", apiKey)
                    //> .addQueryParameter("format", "json")
                    .build()

            val newRequest = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

            chain.proceed(newRequest)
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor).build()

        val retrofit = Retrofit.Builder()
                .baseUrl(urlApi)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return  retrofit.create(ApiService::class.java)
    }
}