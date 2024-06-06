package com.example.pawcarecontrol.security_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.Global
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.model.User.UserClient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val btnLogin = root.findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener{

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userEmail = root.findViewById<TextInputEditText>(R.id.inputUser).text.toString()
                    val userPass = root.findViewById<TextInputEditText>(R.id.inputPass).text.toString()
                    val user = UserClient.service.getUserByEmailAndPass(userEmail, userPass)
                    // Actualizar la UI en el hilo principal
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Inicio de sesión completado", Toast.LENGTH_LONG)
                            .show()
                        Global.userType = user.tipoUsuario.nombre_Tipo_Usuario
                        if (Global.userType == "Administrador") {
                            findNavController().navigate(R.id.action_loginFragment_to_doctors)
                        } else {
                            findNavController().navigate(R.id.action_loginFragment_to_appointments)
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG)
                            .show()
                        println(e)
                    }
                }
            }
        }
        return root
    }
}