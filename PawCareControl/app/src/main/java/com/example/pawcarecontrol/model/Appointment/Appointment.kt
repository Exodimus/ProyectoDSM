package com.example.pawcarecontrol.model.Appointment

import com.example.pawcarecontrol.model.Doctor.PostDoctor
import java.sql.Time
import java.util.Date

data class Appointment(
    val id: Int, //Long
    val fecha: Date,
    val hora: Time,
    val estatus: Long, //Long
    val usuario: Doctor,
    val paciente: Mascota,
    val estadoCita: AppointmentStatus
)
data class AppointmentStatus(
    val id: Int, //Long
    val status: String,
    val Nombre_Estado_Cita: String //Verificar si usar esto o el status
)

// Agregar model.Doctor.kt
data class Doctor(
    val id: Long,
    val name: String
)

// Agregar model.Mascota.kt
data class Mascota(
    val id: Long,
    val name: String
)