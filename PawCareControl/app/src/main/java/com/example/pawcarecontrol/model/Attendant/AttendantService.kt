package com.example.pawcarecontrol.model.Attendant

import retrofit2.http.*

private const val route = "Encargado_Mascota"

interface AttendantService {
    @GET("${route}/All")
    suspend fun getAttendants(): MutableList<Attendant>

    @POST("${route}/Save")
    suspend fun createAttendant(@Body user: PostAttendant): PostAttendant

    @PUT("${route}/DesactivarEncargadoMascota/{id}")
    suspend fun deleteAttendant(@Path("id") id: Int): PostAttendant

    @GET("${route}/Find/{id}")
    suspend fun getAttendant(@Path("id") id: Int): Attendant

    @PUT("${route}/Update/{id}")
    suspend fun updateAttendant(@Path("id") id: Int,@Body user: PostAttendant): PostAttendant
}