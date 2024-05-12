package com.example.pawcarecontrol.create_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.R

class CreateAppointmentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_appointment, container, false)
        val btnAddAppointment = root.findViewById<Button>(R.id.btnAddAppointment)
        val mascots = arrayOf("Perro", "Gato", "Pájaro", "Pez", "Conejo")
        val doctors = arrayOf("Pedro Parker", "Natalia Jimenez", "Brandon Alexis", "Alexa Quintanilla", "Alejandro Gomez")

        val autoCompleteTextViewPet = root.findViewById<AutoCompleteTextView>(R.id.mascota_input)
        val autoCompleteTextViewDoc = root.findViewById<AutoCompleteTextView>(R.id.doctor_input)
        val adapterPet = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, mascots)
        autoCompleteTextViewPet.setAdapter(adapterPet)
        val adapterDoc = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, doctors)
        autoCompleteTextViewDoc.setAdapter(adapterDoc)

        btnAddAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_createAppointmentFragment_to_listAppointmentsFragment)
        }

        return root
    }

}