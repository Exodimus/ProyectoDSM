package com.example.pawcarecontrol.model.Appointment

import com.example.pawcarecontrol.model.BaseClient

object AppointmentClient: BaseClient() {
   val service = retrofit.create(AppointmentService::class.java)
}