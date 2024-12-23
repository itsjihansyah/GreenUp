package com.example.plantapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantapp.R

class SplashFragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Delay for 3 seconds before navigating to the next screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to the next fragment
            findNavController().navigate(R.id.action_splashFragment1_to_splashFragment)
        }, 3000) // 3000ms = 3 seconds
    }
}
