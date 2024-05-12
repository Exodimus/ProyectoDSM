package com.example.pawcarecontrol.security_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.pawcarecontrol.R

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        val btnLogin = root.findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_doctors)
        }
        return root
    }
}