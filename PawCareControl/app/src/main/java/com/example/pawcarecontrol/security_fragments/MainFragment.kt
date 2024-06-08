package com.example.pawcarecontrol.security_fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.Global
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.model.User.UserClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        val signInButton = root.findViewById<Button>(R.id.sign_in_button)

        signInButton.setOnClickListener {
            googleSignIn();
        }

        val btnToLogin = root.findViewById<Button>(R.id.btnToLogin)

        btnToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }
        return root
    }

    private fun googleSignIn() {
        val signInClient = googleSignInClient.signInIntent
        launcher.launch(signInClient)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                manageResults(task)
            }
        }

    private fun manageResults(task: Task<GoogleSignInAccount>) {
        val account: GoogleSignInAccount? = task.result

        if (account != null) {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener {
                if (task.isSuccessful) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val user = UserClient.service.getUserByEmail(account.email.toString())
                            if(user!=null){
                                // Actualizar la UI en el hilo principal
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(requireContext(), "Inicio de sesión completado", Toast.LENGTH_LONG)
                                        .show()
                                    Global.userType = user.tipoUsuario.nombre_Tipo_Usuario
                                    if (Global.userType == "Administrador") {
                                        findNavController().navigate(R.id.action_loginFragment_to_doctors)
                                    } else {
                                        findNavController().navigate(R.id.action_mainFragment_to_appointments)
                                    }
                                }
                            }else{
                                Toast.makeText(requireContext(), "El usuario no esta registrado", Toast.LENGTH_LONG)
                                    .show()
                            }

                        }catch (e: HttpException) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Acceso Denegado: Revise las credenciales o pide que activen tu usuario.", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: IOException) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Error de red. Por favor, revise su conexión.", Toast.LENGTH_LONG).show()
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    /*val data = Bundle()
                    data.putString("username", account.displayName)
                    findNavController().navigate(R.id.action_mainFragment_to_appointments)
                    Toast.makeText(requireContext(), "Iniciado", Toast.LENGTH_SHORT).show()*/

                } else {
                    Toast.makeText(requireContext(), "Iniciado", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}