package com.example.pawcarecontrol.model.Doctor
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DoctorService {
    @GET("Usuario/All/2")
    suspend fun getDoctors(): MutableList<Doctor>

    @POST("Usuario/Save")
    suspend fun createDoctor(@Body user: PostDoctor): Call<PostDoctor>

    @DELETE("Usuario/Delete/{id}")
    suspend fun deleteDoctor(@Path("id") id: Int)
}