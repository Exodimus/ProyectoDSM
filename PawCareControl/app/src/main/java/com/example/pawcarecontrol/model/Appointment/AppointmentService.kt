package com.example.pawcarecontrol.model.Appointment

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

private const val route = "Usuario"

interface AppointmentService {
    @GET("${route}/All/2")
    suspend fun getDoctors(): MutableList<Appointment>

    @POST("${route}/Save")
    suspend fun createDoctor(@Body user: Appointment): Call<Appointment>

    @GET("citas")
    suspend fun getAllAppointments(): Response<List<Appointment>>

    @POST("citas")
    suspend fun saveAppointment(@Body appointment: Appointment): Response<Appointment>

    @PUT("${route}/Update/{id}")
    suspend fun updateDoctor(@Path("id") id: Int, @Body user: Appointment): Response<Appointment>
}