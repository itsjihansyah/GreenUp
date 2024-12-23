package com.example.plantapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.plantapp.databinding.FragmentMissionBinding
import com.example.plantapp.databinding.FragmentMyPlantDetailBinding

class MissionFragment : Fragment() {

    private var isMissionActive = false

    private var _binding: FragmentMissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMissionBinding.inflate(inflater, container, false)


        val missionButton = binding.root.findViewById<View>(R.id.mission)

        missionButton.setOnClickListener {
            isMissionActive = !isMissionActive

            val toastMessage = if (isMissionActive) {
                "Mission is now active!"
            } else {
                "Mission is no longer active."
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}
