package com.example.pawcarecontrol.adapters
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.list_fragments.ListDoctorsFragmentDirections
import com.example.pawcarecontrol.model.Doctor.Doctor
import com.example.pawcarecontrol.model.Doctor.DoctorClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class DoctorsAdapter(private var doctorsList: MutableList<Doctor>,
                     private val context: Context,
                     private val navigationControler: NavController
    ): RecyclerView.Adapter<DoctorsAdapter.DoctorsViewHolder>(){
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
        var genderText = ""

        if(doctor.genero == "Femenino") {
            genderText = "Dra."
            holder.ivImage.setImageResource(R.drawable.ic_female_doctor)
        } else {
            genderText = "Dr."
            holder.ivImage.setImageResource(R.drawable.ic_male_doctor)
        }

        holder.tvName.text = "${genderText} ${doctor.nombres} ${doctor.apellidos}"
        holder.tvPhone.text = doctor.correo

        holder.btnEditDoctor.setOnClickListener{
            navigationControler.navigate(ListDoctorsFragmentDirections.actionListDoctorsFragmentToCreateDoctorFragment(
                DoctorID = doctor.id
            ))
        }

        holder.btnDeleteDoctor.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {

                    val response = DoctorClient.service.deleteDoctor(doctor.id)

                        Toast.makeText(context, "Usuario eliminado con exito", Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun getItemCount() = doctorsList.size

    fun updateDoctors (newDoctors: List<Doctor>) {
        doctorsList.clear()
        doctorsList.addAll(newDoctors)
        notifyDataSetChanged()
    }
}