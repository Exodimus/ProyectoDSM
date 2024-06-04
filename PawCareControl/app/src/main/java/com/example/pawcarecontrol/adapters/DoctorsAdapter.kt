package com.example.pawcarecontrol.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.classes.Doctor

class DoctorsAdapter (private var doctorsList: List<Doctor>,
                      private val context: Context): RecyclerView.Adapter<DoctorsAdapter.DoctorsViewHolder>(){
    class DoctorsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivImage)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvPhone: TextView = view.findViewById(R.id.tvPhone)
        val btnEditDoctor: ImageButton = view.findViewById(R.id.btnEditDoctor)
        val btnDeleteDoctor: ImageButton = view.findViewById(R.id.btnDeleteDoctor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return DoctorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorsViewHolder, position: Int) {
        val doctor = doctorsList[position]
        holder.tvName.text = doctor.name
        holder.tvPhone.text = doctor.phone

        if(doctor.gender == "Femenino") {
            holder.ivImage.setImageResource(R.drawable.ic_female_doctor)
        } else {
            holder.ivImage.setImageResource(R.drawable.ic_male_doctor)
        }
    }

    override fun getItemCount() = doctorsList.size

    fun refreshData (newDoctors: List<Doctor>) {
        doctorsList = newDoctors
        notifyDataSetChanged()
    }
}