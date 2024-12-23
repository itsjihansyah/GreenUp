package com.example.plantapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.plantapp.databinding.FragmentMyPlantDetailBinding
import com.example.plantapp.models.Iot
import com.google.android.gms.tasks.Tasks
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*

class MyPlantDetail : Fragment() {

    private var _binding: FragmentMyPlantDetailBinding? = null
    private val binding get() = _binding!!

    private val args: MyPlantDetailArgs by navArgs()
    private lateinit var deleteIcon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPlantDetailBinding.inflate(inflater, container, false)

        binding.toolbar.leftIcon.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantDetail_to_plantHomeFragment)
        }

        deleteIcon = binding.toolbar.deleteIcon
        deleteIcon.visibility = View.VISIBLE

        deleteIcon.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete item permanently")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes") { _, _ ->
                    deletePlantFromFirebase(args.id)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }


        binding.toolbar.toolbarTitle.text = args.name

        binding.fabEdit.setOnClickListener {
            val action = MyPlantDetailDirections.actionMyPlantDetailToUpdatePlantFragment(
                args.id,
                args.name,
                args.type
            )
            findNavController().navigate(action)
        }

        fetchIoTData(args.id)

        return binding.root
    }

    private fun deletePlantFromFirebase(plantId: String) {
        val firebaseRef = FirebaseDatabase.getInstance().getReference("plants").child(plantId)
        val firebaseIot = FirebaseDatabase.getInstance().getReference("Iot").child(plantId)

        val deletePlantTask = firebaseRef.removeValue()
        val deleteIotTask = firebaseIot.removeValue()

        Tasks.whenAllComplete(deletePlantTask, deleteIotTask).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(requireContext(), "Plant deleted successfully", Toast.LENGTH_SHORT).show()
                val action = MyPlantDetailDirections.actionMyPlantDetailToPlantHomeFragment()
                findNavController().navigate(action) // Navigate only once
            } else {
                Toast.makeText(requireContext(), "Failed to delete plant", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun fetchIoTData(plantId: String) {
        val firebaseRef = FirebaseDatabase.getInstance().getReference("Iot").child(plantId)

        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val iotData = snapshot.getValue(Iot::class.java)
                if (iotData != null) {
                    updateUIWithIoTData(iotData)
                } else {
                    showNoDataAvailable()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }
        })
    }


    private fun updateUIWithIoTData(iotData: Iot) {
        binding.apply {
            temperature.text = iotData.temp?.toString() ?: "N/A"
            moist.text = iotData.moist?.toString() ?: "N/A"
            light.text = iotData.light?.toString() ?: "N/A"

            val health = iotData.health ?: "N/A"
            if (health != "N/A") {
                try {
                    val healthWithoutPercent = health.replace("%", "").trim()
                    val healthInt = healthWithoutPercent.toFloatOrNull()?.toInt()

                    if (healthInt != null) {
                        binding.health.text = "$healthInt%"
                    } else {
                        binding.health.text = "N/A"
                    }
                } catch (e: Exception) {
                    binding.health.text = "N/A"
                    Log.e("Error", "Invalid health value: $health", e)
                }
            } else {
                binding.health.text = "N/A"
            }
        }
    }

    private fun showNoDataAvailable() {
        binding.apply {
            temperature.text = "N/A"
            moist.text = "N/A"
            light.text = "N/A"
            health.text = "N/A"
        }
        Toast.makeText(requireContext(), "IoT device not connected", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
