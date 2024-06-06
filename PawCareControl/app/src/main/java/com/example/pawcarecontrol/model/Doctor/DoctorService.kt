package com.example.pawcarecontrol.model.Doctor
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DoctorService {
    @GET("Usuario/All/2")
    suspend fun getDoctors(): List<Doctor>

    @POST("Usuario/Save")
    fun saveDoctor(@Body user: PostDoctor): Call<PostDoctor>
}