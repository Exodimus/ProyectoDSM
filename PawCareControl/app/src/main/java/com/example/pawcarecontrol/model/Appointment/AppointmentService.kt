package com.example.pawcarecontrol.model.Appointment

import com.example.pawcarecontrol.model.Doctor.Doctor
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

private const val route = "Usuario"

interface AppointmentService {
    @GET("${route}/All")
    suspend fun getAllAppointments(): MutableList<Appointment>

    @GET("${route}/All_Citas_Doctor/{id}/{idE}")
    suspend fun getAllAppointmentsByDoctor(@Path("id") doctorId: Int, @Path("idE") idE: Int): MutableList<Appointment>

    @GET("${route}/All_Citas_Doctor_Pendientes/{id}")
    suspend fun getPendingAppointmentsByDoctor(@Path("id") doctorId: Int): MutableList<Appointment>

    @GET("${route}/All_Citas_Doctor_Finalizadas/{id}")
    suspend fun getCompletedAppointmentsByDoctor(@Path("id") doctorId: Int): MutableList<Appointment>

    @GET("${route}/Find/{id}")
    suspend fun getAppointmentById(@Path("id") appointmentId: Int): Appointment

    @POST("${route}/Save")
    suspend fun saveAppointment(@Body appointment: Appointment): Call<Appointment>

    @PUT("${route}/Update/{id}")
    suspend fun updateAppointment(@Path("id") appointmentId: Int, @Body appointment: Appointment): Response<Appointment>

    @PUT("${route}/DesactivarCita/{id}")
    suspend fun deactivateAppointment(@Path("id") appointmentId: Int): Response<Appointment>
}