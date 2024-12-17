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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantHomeBinding.inflate(inflater, container, false)

        binding.toolbar.toolbarTitle.text = "My Plant"

        binding.fabAdd.setOnClickListener{
            findNavController().navigate(R.id.action_plantHomeFragment_to_addFragment)
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

//    private fun fetchData() {
//        firebaseRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                plantsList.clear()
//                searchList.clear()
//                if (snapshot.exists()) {
//                    for (plantSnap in snapshot.children) {
//                        val plants = plantSnap.getValue(Plants::class.java)
//                        if (plants != null) {
//                            // Fetch IoT data for the plant
//                            val iotRef = FirebaseDatabase.getInstance().getReference("Iot").child(plants.id!!)
//                            iotRef.addListenerForSingleValueEvent(object : ValueEventListener {
//
//                                override fun onDataChange(iotSnapshot: DataSnapshot) {
//                                    val iot = iotSnapshot.getValue(Iot::class.java)
//                                    if (iot != null) {
//                                        iot.health = calculateHealthPercentage(iot.temp, iot.light, iot.moist)
//                                        plantsList.add(plants)
//                                        searchList.add(plants)
//                                        rvAdapter.notifyDataSetChanged()
//                                    }
//                                }
//
//                                override fun onCancelled(error: DatabaseError) {
//                                    Toast.makeText(context, "Failed to fetch IoT data: $error", Toast.LENGTH_SHORT).show()
//                                }
//                            })
//                        }
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

    private fun fetchData() {
        firebaseRef.addValueEventListener(object : ValueEventListener {
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
        })
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

