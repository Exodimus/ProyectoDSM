package com.example.pawcarecontrol.model.User

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.13:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(UserService::class.java)
}