package com.example.pawcarecontrol.create_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.R
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import com.example.pawcarecontrol.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class CreateAppointmentFragment : Fragment() {
    private lateinit var calendarView: CalendarView
    private lateinit var spinnerDoctor: Spinner
    private lateinit var spinnerMascota: Spinner
    private lateinit var btnAddAppointment: Button
    private var selectedDate: Date = Date()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_appointment, container, false)
        val btnAddAppointment = root.findViewById<Button>(R.id.btnAddAppointment)


        btnAddAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_createAppointmentFragment_to_listAppointmentsFragment)
        }


        return root
    }

}