package com.example.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.plantapp.databinding.FragmentQuizMenuBinding
import com.example.plantapp.databinding.FragmentSignupBinding

class QuizMenuFragment : Fragment() {
    private var _binding: FragmentQuizMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizMenuBinding.inflate(inflater, container, false)

        binding.btnPlay.setOnClickListener {
            findNavController().navigate(R.id.action_quizMenuFragment_to_quizFragment)
        }

        binding.toolbar.toolbarTitle.text = "Quiz"

        binding.toolbar.leftIcon.setOnClickListener {
            findNavController().navigate(R.id.action_quizMenuFragment_to_homeFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}