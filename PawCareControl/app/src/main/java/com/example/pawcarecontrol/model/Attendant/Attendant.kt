package com.example.pawcarecontrol.model.Attendant

data class Attendant(
    val apellidos: String,
    val ciudad: String,
    val direccion: String,
    val dui: String,
    val edad: String,
    val estatus: Int,
    val id: Int,
    val nombres: String
)

data class PostAttendant(
    val nombres: String,
    val apellidos: String,
    val dui: String,
    val edad: String,
    val ciudad: String,
    val direccion: String,
    val estatus: Int,

)