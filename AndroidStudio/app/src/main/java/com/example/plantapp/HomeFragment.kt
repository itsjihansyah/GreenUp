package com.example.plantapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantapp.databinding.FragmentHomeBinding
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPlant.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_plantHomeFragment)
        }

        binding.quiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_quizMenuFragment)
        }

        binding.reedem.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_reedemFragment)
        }

        binding.reminder.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_generateTextBotFragment)
        }

        fetchRealtimeDatabaseData("-OD6dujd6CdaWRoxq0j7")
    }

    private var databaseListener: ValueEventListener? = null

    private fun fetchRealtimeDatabaseData(id: String) {
        database = FirebaseDatabase.getInstance().getReference("Iot/$id")

        databaseListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val health = snapshot.child("health").value?.toString() ?: "N/A"
                updateHealthPercentage(health)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Failed to fetch data: ${error.message}")
            }
        }

        database.addValueEventListener(databaseListener!!)
    }


    private fun updateHealthPercentage(health: String) {
        _binding?.apply {
            if (health != "N/A") {
                try {
                    val healthWithoutPercent = health.replace("%", "").trim()
                    val healthInt = healthWithoutPercent.toFloatOrNull()?.toInt()

                    if (healthInt != null) {
                        plantHealth.text = "$healthInt%"
                    } else {
                        plantHealth.text = "N/A"
                    }
                } catch (e: Exception) {
                    plantHealth.text = "N/A"
                    Log.e("Error", "Invalid health value: $health", e)
                }
            } else {
                plantHealth.text = "N/A"
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // Remove the Firebase listener if it exists
        databaseListener?.let { database.removeEventListener(it) }
    }

}
