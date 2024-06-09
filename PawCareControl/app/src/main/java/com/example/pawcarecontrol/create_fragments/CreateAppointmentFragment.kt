package com.example.pawcarecontrol.create_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.model.Appointment.Appointment
import com.example.pawcarecontrol.model.Appointment.AppointmentClient
import com.example.pawcarecontrol.model.Doctor.DoctorClient
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pawcarecontrol.model.*
import com.example.pawcarecontrol.model.Doctor.Doctor
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Response
import java.util.*

class CreateAppointmentFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var textDoctor: TextInputEditText
    private lateinit var spinnerMascota: Spinner
    private lateinit var btnAddAppointment: Button
    private var selectedDate: Date = Date()
    private val args: CreateDoctorFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_appointment, container, false)
        calendarView = root.findViewById(R.id.calendarView)
        textDoctor = root.findViewById(R.id.editTextDoctor)
        spinnerMascota = root.findViewById(R.id.spinnerMascota)
        btnAddAppointment = root.findViewById(R.id.btnAddAppointment)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.time
        }
        val timePicker = root.findViewById<TimePicker>(R.id.timePicker)

        getDoctor { doctor ->
            doctor?.let{
                val fullName = "${it.nombres} ${it.apellidos}"
                view?.findViewById<TextInputEditText>(R.id.editTextDoctor)?.setText(fullName)
            }?: run {
                Toast.makeText(requireContext(), "No se pudo obtener la información del doctor", Toast.LENGTH_LONG).show()
            }
        }

        //loadMascotas()

        btnAddAppointment.setOnClickListener {
            val hour = timePicker.hour // Obtiene la hora (0-23)
            val minute = timePicker.minute // Obtiene los minutos (0-59)
            val selectedTime = String.format("%02d:%02d", hour, minute)
            saveAppointment(selectedTime) // Pasa selectedTime como argumento
        }

        return root
    }

    /*private fun loadMascotas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = DoctorClient.service.getDoctors()
                if (response.isSuccessful) {
                    val doctors = response.body()
                    if (doctors != null && doctors.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            val adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                doctors.map { doctor -> doctor.name }
                            )
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            spinnerMascota.adapter = adapter
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(requireContext(), "No se encontraron doctores", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error al obtener la lista de doctores", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/

    private fun saveAppointment(selectedTime: String) {
        val mascotaId = (spinnerMascota.selectedItem as Mascota).id  //  Borrar clase y poner la que corresponde
        val doctorId = (textDoctor as Doctor).id
        val estadoCita = 1  // Estado por defecto activo
        val appointment = Appointment(
            fecha = selectedDate,
            hora = selectedTime,
            doctor = doctorId,
            mascota = mascotaId,
            estadoCita = estadoCita,
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<Appointment> = AppointmentClient.service.saveAppointment(appointment).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Cita guardada exitosamente", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.action_createAppointmentFragment_to_listAppointmentsFragment)
                    } else {
                        Toast.makeText(context, "Error al guardar la cita", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun getDoctor(callback: (Doctor?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val doctor = DoctorClient.service.getDoctor(args.DoctorID)
                withContext(Dispatchers.Main) {
                    callback(doctor)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback(null)
                }
            }
        }
    }
}