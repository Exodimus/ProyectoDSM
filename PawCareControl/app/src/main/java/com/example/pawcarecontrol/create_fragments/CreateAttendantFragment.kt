package com.example.pawcarecontrol.create_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.databinding.FragmentCreateAttendantBinding
import com.example.pawcarecontrol.model.Attendant.Attendant
import com.example.pawcarecontrol.model.Attendant.AttendantClient
import com.example.pawcarecontrol.model.Attendant.PostAttendant
import com.example.pawcarecontrol.model.Doctor.DoctorClient
import com.example.pawcarecontrol.model.Doctor.PostDoctor
import com.example.pawcarecontrol.model.User.UserType
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class CreateAttendantFragment : Fragment() {
    private lateinit var binding: FragmentCreateAttendantBinding

    private val args: CreateAttendantFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAttendant{attendant->
            attendant?.let{
                view.findViewById<TextInputEditText>(R.id.inputFirstName).setText(attendant.nombres)
                view.findViewById<TextInputEditText>(R.id.inputLastName).setText(attendant.apellidos)
                view.findViewById<TextInputEditText>(R.id.inputAddress).setText(attendant.direccion)
                view.findViewById<TextInputEditText>(R.id.inputCity).setText(attendant.ciudad)
                view.findViewById<TextInputEditText>(R.id.inputAge).setText(attendant.edad)
                view.findViewById<TextInputEditText>(R.id.inputDui).setText(attendant.dui)
            }?: run {
                Toast.makeText(requireContext(), "No se pudo obtener la información del Encargado", Toast.LENGTH_LONG).show()
            }

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAttendantBinding.inflate(inflater, container, false)
        val root = binding.root

        if (args.AttendantID != -1) {
            root.findViewById<TextView>(R.id.attendandtTittle).setText("Editar registro Encargado")
        }

        val btnAddAttendant= root.findViewById<Button>(R.id.btnAddAttendant)
        val btnCancelAttendant = root.findViewById<Button>(R.id.btnCancelAttendant)

        btnAddAttendant.setOnClickListener{
            val firstName = root.findViewById<TextInputEditText>(R.id.inputFirstName).text.toString()
            val lastName = root.findViewById<TextInputEditText>(R.id.inputLastName).text.toString()
            val address = root.findViewById<TextInputEditText>(R.id.inputAddress).text.toString()
            val age = root.findViewById<TextInputEditText>(R.id.inputAge).text.toString()
            val city = root.findViewById<TextInputEditText>(R.id.inputCity).text.toString()
            val dui = root.findViewById<TextInputEditText>(R.id.inputDui).text.toString()
            val estado = 1

            if (firstName.isEmpty()
                || lastName.isEmpty()
                || address.isEmpty()
                || age.isEmpty()
                || city.isEmpty()
                || dui.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor complete todos los campos.", Toast.LENGTH_LONG).show()
            } else {
                if (args.AttendantID == -1) {
                    val attendant = PostAttendant(
                        firstName,
                        lastName,
                        dui,
                        age,
                        city,
                        address,
                        estado ,
                    )
                    createAttendant(attendant)
                } else {
                    val attendant = PostAttendant(
                        firstName,
                        lastName,
                        dui,
                        age,
                        city,
                        address,
                        estado ,
                    )
                    updateAttendant(attendant)
                }
            }
        }
        btnCancelAttendant.setOnClickListener{
            findNavController().navigate(R.id.action_createAttendantFragment_to_listAttendantsFragment)
        }

        return root
    }

    private fun createAttendant(attendant: PostAttendant) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: PostAttendant = AttendantClient.service.createAttendant(attendant)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Encargado guardado exitosamente", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_createAttendantFragment_to_listAttendantsFragment)
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error del servidor: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de red. Por favor, revise su conexión.", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("CreateAttendantFragment", "Error desconocido: ${e.message}", e)
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateAttendant(attendant: PostAttendant) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: PostAttendant = AttendantClient.service.updateAttendant(args.AttendantID,attendant)

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Encargado Actualizado exitosamente", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_createAttendantFragment_to_listAttendantsFragment)
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error del servidor: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de red. Por favor, revise su conexión.", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("CreateAttendantFragment", "Error desconocido: ${e.message}", e)
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getAttendant(callback: (Attendant?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val attendant = AttendantClient.service.getAttendant(args.AttendantID)
                withContext(Dispatchers.Main) {
                    callback(attendant)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback(null)
                }
            }
        }
    }
}