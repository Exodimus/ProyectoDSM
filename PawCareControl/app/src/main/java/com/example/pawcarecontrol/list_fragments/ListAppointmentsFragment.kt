package com.example.pawcarecontrol.list_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pawcarecontrol.Global
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.adapters.AppointmentsAdapter
import com.example.pawcarecontrol.create_fragments.CreateDoctorFragmentArgs
import com.example.pawcarecontrol.model.Appointment.Appointment
import com.example.pawcarecontrol.model.Appointment.AppointmentClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListAppointmentsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppointmentsAdapter
    private val appointmentsList = mutableListOf<Appointment>()
    private val args: CreateDoctorFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list_appointments, container, false)
        val btnCreateAppointment = root.findViewById<ExtendedFloatingActionButton>(R.id.btnCreateAppointment)

        recyclerView = root.findViewById(R.id.recyclerViewAppointments)
        adapter = AppointmentsAdapter(requireContext(), appointmentsList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        getPendingAppointmentsByDoctor()

        btnCreateAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_listAppointmentsFragment_to_createAppointmentFragment)
        }

        val bottomNavigation = root.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (Global.userType.toString() != "Administrador") {
            bottomNavigation.menu.findItem(R.id.page_1).isVisible = false
        }
        bottomNavigation.selectedItemId = R.id.page_2

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    findNavController().navigate(R.id.action_global_doctors2)
                    true
                }

                R.id.page_2 -> {
                    findNavController().navigate(R.id.action_global_appointments2)
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

    private fun getPendingAppointmentsByDoctor() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = AppointmentClient.service.getPendingAppointmentsByDoctor(args.DoctorID)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val appointments = response.body() ?: emptyList()
                        adapter.updateAppointments(appointments)
                    } else {
                        Toast.makeText(context, "Error al cargar las citas", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
