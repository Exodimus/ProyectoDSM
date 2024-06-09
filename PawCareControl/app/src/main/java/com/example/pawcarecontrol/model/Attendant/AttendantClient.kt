package com.example.pawcarecontrol.model.Attendant

import com.example.pawcarecontrol.model.BaseClient

object AttendantClient: BaseClient() {
   val service = retrofit.create(AttendantService::class.java)
}