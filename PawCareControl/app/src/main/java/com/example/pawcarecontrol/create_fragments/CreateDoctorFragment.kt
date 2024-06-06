package com.example.pawcarecontrol.create_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.databinding.FragmentCreateDoctorBinding
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

class CreateDoctorFragment : Fragment() {
    private lateinit var binding: FragmentCreateDoctorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateDoctorBinding.inflate(inflater, container, false)
        val root = binding.root

        val genders = resources.getStringArray(R.array.genders)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.autoCompleteGenders.setAdapter(arrayAdapter)

        val btnAddDoctor= root.findViewById<Button>(R.id.btnAddDoctor)
        val btnCancelDoctor = root.findViewById<Button>(R.id.btnCancelDoctor)

        btnAddDoctor.setOnClickListener{
            val firstName = root.findViewById<TextInputEditText>(R.id.inputFirstName).text.toString()
            val lastName = root.findViewById<TextInputEditText>(R.id.inputLastName).text.toString()
            val email = root.findViewById<TextInputEditText>(R.id.inputEmail).text.toString()
            val password = root.findViewById<TextInputEditText>(R.id.inputPass).text.toString()
            val gender = root.findViewById<AutoCompleteTextView>(R.id.autoCompleteGenders).text.toString()

            if (firstName.isEmpty()
                || lastName.isEmpty()
                || password.isEmpty()
                || gender.isEmpty()
                || email.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor complete todos los campos.", Toast.LENGTH_LONG).show()
            } else {
                val doctor = PostDoctor(
                    firstName,
                    lastName,
                    password,
                    email,
                    1,
                    UserType(1,2, "Veterinario"))

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response: Response<PostDoctor> = DoctorClient.service.createDoctor(doctor).execute()

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "Usuario guardado exitosamente", Toast.LENGTH_LONG).show()
                                // Realizar la acción deseada después de guardar el usuario
                                findNavController().navigate(R.id.action_createDoctorFragment_to_listDoctorsFragment)
                            } else {
                                Toast.makeText(requireContext(), "Error del servidor: ${response.errorBody()?.string()}", Toast.LENGTH_LONG).show()
                            }
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
                            Log.e("CreateDoctorFragment", "Error desconocido: ${e.message}", e)
                            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        btnCancelDoctor.setOnClickListener{
            findNavController().navigate(R.id.action_createDoctorFragment_to_listDoctorsFragment)
        }

        return root
    }
}