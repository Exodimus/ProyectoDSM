package com.example.pawcarecontrol.list_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ListDoctorsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list_doctors, container, false)

        val btnCreateDoctor = root.findViewById<ExtendedFloatingActionButton>(R.id.btnCreateDoctor)

        btnCreateDoctor.setOnClickListener{
            findNavController().navigate(R.id.action_listDoctorsFragment_to_createDoctorFragment)
        }

        val bottomNavigation = root.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.selectedItemId = R.id.page_1

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    true
                }

                R.id.page_2 -> {
                    findNavController().navigate(R.id.action_global_appointments)
                    true
                }

                R.id.page_3 -> {
                    findNavController().navigate(R.id.action_global_pets)
                    true
                }

                else -> false
            }
        }

        return root
    }
}