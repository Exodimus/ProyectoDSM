package com.example.pawcarecontrol.model.Doctor

import retrofit2.http.GET
import retrofit2.http.Path

interface DoctorService {
    @GET("Usuario/All")
    suspend fun getDoctors(): List<Doctor>
}