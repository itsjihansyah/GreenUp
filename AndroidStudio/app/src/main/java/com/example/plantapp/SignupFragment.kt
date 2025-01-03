package com.example.plantapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.example.plantapp.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireActivity()) // Initialize the loading dialog

        binding.btnSignUp.setOnClickListener {
            loadingDialog.startLoadingDialog() // Show the loading dialog

            // Simulate a delay (e.g., network request or processing) before navigation
            binding.btnSignUp.postDelayed({
                loadingDialog.dismissDialog() // Dismiss the loading dialog
                findNavController().navigate(R.id.action_signupFragment_to_homeFragment)
            }, 2000) // Adjust delay as needed
        }

        return binding.root
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus
        view?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
