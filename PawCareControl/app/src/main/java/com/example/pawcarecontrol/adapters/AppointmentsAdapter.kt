package com.example.pawcarecontrol.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.model.Appointment.Appointment
class AppointmentsAdapter(
    private val context: Context,
    private val appointmentsList: MutableList<Appointment>
) : RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val textViewDoctor: TextView = view.findViewById(R.id.textViewDoctor)
        val textViewStatus: TextView = view.findViewById(R.id.textViewStatus)
        val textViewPacient: TextView = view.findViewById(R.id.textViewPacient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointmentsList[position]
        holder.textViewDate.text = "Fecha: ${appointment.fecha}"
        holder.textViewDoctor.text = "Doctor: ${appointment.doctor}"
        holder.textViewStatus.text = "Estado: ${appointment.estadoCita}"
        holder.textViewPacient.text = "Mascota: ${appointment.mascota}"
    }

    override fun getItemCount(): Int {
        return appointmentsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAppointments(newAppointments: List<Appointment>) {
        appointmentsList.clear()
        appointmentsList.addAll(newAppointments)
        notifyDataSetChanged()
    }
}