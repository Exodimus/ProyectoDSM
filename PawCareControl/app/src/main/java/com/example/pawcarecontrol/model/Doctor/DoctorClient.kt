package com.example.pawcarecontrol.model.Doctor

import com.example.pawcarecontrol.model.BaseClient
import com.example.pawcarecontrol.model.User.UserService

object DoctorClient: BaseClient() {
   val service = retrofit.create(UserService::class.java)
}