package com.example.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.plantapp.databinding.FragmentAddPlantBinding
import com.example.plantapp.models.Iot
import com.example.plantapp.models.Plants
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPlantFragment : Fragment() {
    private var _binding: FragmentAddPlantBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseRef: DatabaseReference
    private lateinit var iotRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPlantBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("plants")
        iotRef = FirebaseDatabase.getInstance().getReference("Iot")


        binding.toolbar.toolbarTitle.text = "Add Plant"

        binding.toolbar.leftIcon.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            saveData()
        }

        return binding.root
    }

    private fun saveData() {
        val name = binding.addPlantName.text.toString().trim()
        val type = binding.addPlantType.text.toString().trim()

        if (name.isEmpty()) {
            binding.addPlantName.error = "Write a name"
            return
        }
        if (type.isEmpty()) {
            binding.addPlantType.error = "Write a plant type"
            return
        }

        // Create a unique ID for the plant and save it
        val plantId = firebaseRef.push().key!!

        val plants = Plants(plantId, name, type)

        // Save plant data to Firebase
        firebaseRef.child(plantId).setValue(plants)
            .addOnCompleteListener {
                // Once the plant data is saved, create default IoT data
                val defaultIot = Iot(
                    id = plantId,         // Use the same plant ID for IoT
                    temp = 0,             // Default temperature
                    light = 0,            // Default light
                    moist = 0,            // Default moisture
                    health = "N/A"        // Default health
                )

                // Save the IoT data to Firebase
                iotRef.child(plantId).setValue(defaultIot)
                    .addOnCompleteListener {
                        Toast.makeText(context, "Data stored successfully", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp() // Go back after success
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Failed to store IoT data: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Failed to store plant data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

}