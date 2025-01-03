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

class PlantHomeFragment : Fragment() {
    private var _binding: FragmentPlantHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchList: ArrayList<Plants>
    private lateinit var rvAdapter: RvPlantAdapter
    private lateinit var plantsList:ArrayList<Plants>
    private lateinit var firebaseRef : DatabaseReference
    private lateinit var firebaseListener: ValueEventListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantHomeBinding.inflate(inflater, container, false)

        binding.toolbar.toolbarTitle.text = "My Plant"

        binding.fabAdd.setOnClickListener{
            findNavController().navigate(R.id.action_plantHomeFragment_to_addFragment)
        }

        binding.toolbar.leftIcon.setOnClickListener {
            findNavController().navigate(R.id.action_plantHomeFragment_to_homeFragment)

        }

        firebaseRef = FirebaseDatabase.getInstance().getReference("plants")
        plantsList = arrayListOf()
        searchList = arrayListOf()

        fetchData()

        binding.rvPlant.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }

        rvAdapter = RvPlantAdapter(searchList)
        binding.rvPlant.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }

        setupSearchView()

        return binding.root
    }

    // penanda
    private fun fetchData() {
        firebaseListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                plantsList.clear()
                searchList.clear()
                if (snapshot.exists()) {
                    for (plantSnap in snapshot.children) {
                        val plants = plantSnap.getValue(Plants::class.java)
                        plants?.let { plantsList.add(it) }
                    }
                    searchList.addAll(plantsList)
                    rvAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        }
        firebaseRef.addValueEventListener(firebaseListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        firebaseRef.removeEventListener(firebaseListener) // Detach the listener
        _binding = null
    }

    private fun setupSearchView() {
        binding.search.clearFocus()
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText?.lowercase(Locale.getDefault()).orEmpty()
                searchList.clear()
                if (searchText.isNotEmpty()) {
                    plantsList.forEach { plant ->
                        val nameMatches = plant.name?.lowercase(Locale.getDefault())?.contains(searchText) == true
                        val typeMatches = plant.type?.lowercase(Locale.getDefault())?.contains(searchText) == true
                        if (nameMatches || typeMatches) {
                            searchList.add(plant)
                        }
                    }
                } else {
                    searchList.addAll(plantsList)
                }
                rvAdapter.notifyDataSetChanged()
                return false
            }
        })
    }
}

