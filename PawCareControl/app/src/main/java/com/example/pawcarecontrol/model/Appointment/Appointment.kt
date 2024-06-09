package com.example.pawcarecontrol.model.Appointment

import java.util.Date

data class Appointment(
    val fecha: Date,
    val hora: String,
    val doctor: Int,
    val mascota: Int,
    val estadoCita: Int
)
data class AppointmentStatus(
    val id: Int,
    val status: Int,
    val Nombre_Estado_Cita: String
)