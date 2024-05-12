package com.example.pawcarecontrol.list_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ListAppointmentsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list_appointments, container, false)

        val btnCreateAppointment = root.findViewById<ExtendedFloatingActionButton>(R.id.btnCreateAppointment)

        btnCreateAppointment.setOnClickListener{
            findNavController().navigate(R.id.action_listAppointmentsFragment_to_createAppointmentFragment)
        }

        val bottomNavigation = root.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.selectedItemId = R.id.page_2

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    findNavController().navigate(R.id.action_global_doctors2)
                    true
                }

                R.id.page_2 -> {
                    true
                }

                R.id.page_3 -> {
                    findNavController().navigate(R.id.action_global_pets2)
                    true
                }

                else -> false
            }
        }

        return root
    }
}