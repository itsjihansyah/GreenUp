package com.example.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantapp.databinding.FragmentAddPlantBinding
import com.example.plantapp.databinding.FragmentPlantHomeBinding
import com.example.plantapp.models.Iot
import com.example.plantapp.models.Plants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class Mission : Fragment() {
    private var _binding: FragmentPlantHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchList: ArrayList<Plants>
    private lateinit var rvAdapter: RvPlantAdapter
    private lateinit var plantsList: ArrayList<Plants>
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantHomeBinding.inflate(inflater, container, false)

        binding.toolbar.toolbarTitle.text = "My Plant"

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_plantHomeFragment_to_addFragment)
        }

        return binding.root
    }
}