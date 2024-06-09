package com.example.pawcarecontrol.model

import java.util.*

data class Mascota(
    val id: Int,
    val fecha: Date,
    val hora: String,
    val doctor: Int,
    val mascota: Int,
    val estadoCita: Int
)