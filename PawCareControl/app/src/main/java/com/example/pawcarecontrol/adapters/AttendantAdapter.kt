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
import com.example.pawcarecontrol.list_fragments.ListAttendantsFragmentDirections
import com.example.pawcarecontrol.model.Attendant.Attendant
import com.example.pawcarecontrol.model.Attendant.AttendantClient


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class AttendantAdapter (private var attendatList: MutableList<Attendant>,
                        private val context: Context,
                        private val navigationControler: NavController
    ): RecyclerView.Adapter<AttendantAdapter.AttendantViewHolder>(){
    class AttendantViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivImage2)
        val tvName: TextView = view.findViewById(R.id.tvName3)
        val tvLastNameA: TextView = view.findViewById(R.id.tvLastNameA)
        val tvDui: TextView = view.findViewById(R.id.tvDui)
        val tvAge: TextView = view.findViewById(R.id.tvAge)
        val tvCity: TextView = view.findViewById(R.id.tvCity)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val btnEditAttendant: ImageButton = view.findViewById(R.id.btnEditAttendant)
        val btnDeleteAttendant: ImageButton = view.findViewById(R.id.btnDeleteAttendant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendant, parent, false)
        return AttendantViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendantViewHolder, position: Int) {
        val attendant = attendatList[position]


        holder.tvName.text =attendant.nombres
        holder.tvLastNameA.text = attendant.apellidos
        holder.tvDui.text = attendant.dui
        holder.tvAge.text = attendant.edad
        holder.tvCity.text = attendant.ciudad
        holder.tvAddress.text = attendant.direccion


        holder.btnEditAttendant.setOnClickListener{
            navigationControler.navigate(ListAttendantsFragmentDirections.actionListAttendantsFragmentToCreateAttendantFragment(
                AttendantID = attendant.id

            ))
        }

        holder.btnDeleteAttendant.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = AttendantClient.service.deleteAttendant(attendant.id)

                    withContext(Dispatchers.Main) {
                        // Eliminación exitosa
                        attendatList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, attendatList.size)
                        Toast.makeText(context, "Encargador eliminado con exito", Toast.LENGTH_LONG).show()
                    }
                } catch (e: HttpException) {
                    // Error de HTTP
                    withContext(Dispatchers.Main) {
                        Log.e("DoctorsAdapter", "Error del servidor: ${e.message}", e)
                        Toast.makeText(context, "Error del servidor: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                } catch (e: IOException) {
                    // Error de red
                    withContext(Dispatchers.Main) {
                        Log.e("DoctorsAdapter", "Error de red: ${e.message}", e)
                        Toast.makeText(context, "Error de red. Por favor, revise su conexión.", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    // Otros errores
                    withContext(Dispatchers.Main) {
                        Log.e("DoctorsAdapter", "Error desconocido: ${e.message}", e)
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun getItemCount() = attendatList.size

    fun updateAttendant (newAttendant: List<Attendant>) {
        attendatList.clear()
        attendatList.addAll(newAttendant)
        notifyDataSetChanged()
    }
}