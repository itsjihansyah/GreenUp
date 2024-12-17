package com.example.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.plantapp.databinding.FragmentPlantHomeBinding
import com.example.plantapp.databinding.FragmentUpdatePlantBinding
import com.example.plantapp.models.Plants
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdatePlantFragment : Fragment() {

    private var _binding: FragmentUpdatePlantBinding? = null
    private val binding get() = _binding!!

    private val args : UpdatePlantFragmentArgs by navArgs()

    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdatePlantBinding.inflate(inflater, container, false)

        firebaseRef = FirebaseDatabase.getInstance().getReference("plants")

        binding.apply {
            binding.toolbar.toolbarTitle.text = "Update Plant"

            updatePlantName.setText(args.name)
            updatePlantType.setText(args.type)

            saveButton.setOnClickListener{
                updateData()

                val action = UpdatePlantFragmentDirections.actionUpdatePlantFragmentToMyPlantDetail(
                    args.id,
                    binding.updatePlantName.text.toString(),
                    binding.updatePlantType.text.toString()
                )
                findNavController().navigate(action)
            }

            toolbar.leftIcon.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
        return binding.root
    }

    private fun updateData() {
        val name = binding.updatePlantName.text.toString()
        val type = binding.updatePlantType.text.toString()

        val plants = Plants(args.id, name, type)

//        parsing data to db
        firebaseRef.child(args.id).setValue(plants)

            // check whether received or not
            .addOnCompleteListener{
                Toast.makeText(context, "data updated successfully", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_updatePlantFragment_to_myPlantDetail)
            }
            .addOnFailureListener{
                Toast.makeText(context, "error ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}