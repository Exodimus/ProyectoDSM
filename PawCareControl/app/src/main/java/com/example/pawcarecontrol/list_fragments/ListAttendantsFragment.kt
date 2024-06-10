package com.example.pawcarecontrol.list_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pawcarecontrol.R
import com.example.pawcarecontrol.adapters.AttendantAdapter
import com.example.pawcarecontrol.model.Attendant.Attendant
import com.example.pawcarecontrol.model.Attendant.AttendantClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListAttendantsFragment : Fragment() {
    private lateinit var attendantsAdapter: AttendantAdapter
    private lateinit var attendants : MutableList<Attendant>

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val root = inflater.inflate(R.layout.fragment_list_attendants, container, false)
        val recyclerViewAttendant = root.findViewById<RecyclerView>(R.id.AttendatsContainer)
        val btnCreateAttendant = root.findViewById<ExtendedFloatingActionButton>(R.id.btnCreateAttendant)
        val bottomNavigation = root.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navigationController = findNavController()

        val context = requireContext()

        attendants = mutableListOf()
        attendantsAdapter = AttendantAdapter(attendants, context, navigationController)
        recyclerViewAttendant.layoutManager = LinearLayoutManager(context)
        recyclerViewAttendant.adapter = attendantsAdapter

        getAttendant { attendantList ->
            attendantsAdapter.updateAttendant(attendantList)
        }

        btnCreateAttendant.setOnClickListener {
            navigationController.navigate(ListAttendantsFragmentDirections.actionListAttendantsFragmentToCreateAttendantFragment(
               AttendantID = -1
            ))
        }

        bottomNavigation.selectedItemId = R.id.page_4

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    navigationController.navigate(R.id.action_global_doctor)
                    true
                }
                R.id.page_2 -> {
                    navigationController.navigate(R.id.action_global_appointments)
                    true
                }
                R.id.page_3 -> {
                    navigationController.navigate(R.id.action_global_pets)
                    true
                } R.id.page_4 ->{ true
            } else -> false
            }
        }

        return root
    }

    private fun getAttendant(callback: (List<Attendant>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                attendants = AttendantClient.service.getAttendants()
                withContext(Dispatchers.Main) {
                    attendantsAdapter.updateAttendant(attendants)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback(emptyList())
                }
            }
        }
    }
}

