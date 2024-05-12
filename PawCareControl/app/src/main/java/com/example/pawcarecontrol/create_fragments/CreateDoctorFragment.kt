package com.example.pawcarecontrol.create_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.Helpers.DatePickerHelper
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.databinding.FragmentCreateDoctorBinding

class CreateDoctorFragment : Fragment() {
    private lateinit var binding: FragmentCreateDoctorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateDoctorBinding.inflate(inflater, container, false)
        val root = binding.root

        val genders = resources.getStringArray(R.array.genders)
        val inputBirthday: TextView = binding.inputBirthday

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.autoCompleteGenders.setAdapter(arrayAdapter)

        val btnAddDoctor= root.findViewById<Button>(R.id.btnAddDoctor)

        btnAddDoctor.setOnClickListener{
            findNavController().navigate(R.id.action_createDoctorFragment_to_listDoctorsFragment)
        }


        DatePickerHelper(inputBirthday, childFragmentManager)

        return root
    }
}