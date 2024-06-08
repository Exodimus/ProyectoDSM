package com.example.pawcarecontrol.model.User

import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("Usuario/FindByCorreoAndPass/{correo}/{contra}")
    suspend fun getUserByEmailAndPass(@Path("correo")correo:String, @Path("contra")contra:String): User

    @GET("Usuario/FindByCorreo/{correo}")
    suspend fun getUserByEmail(@Path("correo")correo:String): User
}