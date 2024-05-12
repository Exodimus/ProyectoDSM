package com.example.pawcarecontrol.create_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.R

class CreateAppointmentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_appointment, container, false)

        val btnAddAppointment = root.findViewById<Button>(R.id.btnAddAppointment)

        btnAddAppointment.setOnClickListener{
            findNavController().navigate(R.id.action_createAppointmentFragment_to_listAppointmentsFragment)
        }

        return root
    }
}