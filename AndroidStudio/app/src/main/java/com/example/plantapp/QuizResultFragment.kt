package com.example.plantapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.databinding.FragmentMyPlantDetailBinding
import com.example.plantapp.databinding.FragmentPlantHomeBinding
import com.example.plantapp.databinding.FragmentQuizMenuBinding
import com.example.plantapp.databinding.FragmentQuizResultBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.plantapp.repository.QuestionRepository

class QuizResultFragment : Fragment() {
    private lateinit var questionRecyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionAdapter

    private lateinit var scoreTextView: TextView
    private lateinit var textResult: TextView
    private lateinit var scoreProgressBar: ProgressBar
    private lateinit var pointTv: TextView

    private var progressCounter = 0 // Counter for progress animation
    private lateinit var database: DatabaseReference
    private var correctAnswers = 0
    private var totalQuestions = 10 // Assuming there are 10 questions in the quiz

    private var _binding: FragmentQuizResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Correct binding inflating using FragmentQuizResultBinding
        _binding = FragmentQuizResultBinding.inflate(inflater, container, false)

        binding.ivClose.setOnClickListener {
            findNavController().navigate(R.id.action_quizResultFragment_to_quizMenuFragment)
        }

        database = FirebaseDatabase.getInstance().reference.child("UserAnswers")

        val userId = "1234" // Example user ID

        database.child(userId).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                // Extract correctAnswers and totalQuestions from the snapshot
                correctAnswers = snapshot.child("totalCorrectAnswers").getValue(Int::class.java) ?: 0
                totalQuestions = snapshot.child("totalQuestions").getValue(Int::class.java) ?: 10 // Default to 10 if not available

                // Calculate the percentage of correct answers
                val progress = (correctAnswers.toFloat() / totalQuestions.toFloat()) * 100 // Convert to percentage

                // Update the UI with the score
                scoreTextView = binding.progressText
                scoreTextView.text = "$correctAnswers/$totalQuestions"

                pointTv = binding.tvPoint

                val score = correctAnswers * 10
                pointTv.text = "+$score"

                textResult = binding.textResult
                textResult.text = "Answered $correctAnswers out of $totalQuestions questions"

                // Initialize ProgressBar for animated progress
                scoreProgressBar = binding.progressBar
                scoreProgressBar.max = 100 // Set the max value of the progress bar to 100 for percentage

                // Animation logic with smoother transition
                animateProgressBar(progress.toInt()) // Call the animation function
            }
        }.addOnFailureListener {
            // Handle failure
        }

        // Set up RecyclerView for question list
        questionRecyclerView = binding.recyclerQuestions
        questionRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val questionList = QuestionRepository.questionList ?: emptyList()

        questionAdapter = QuestionAdapter(questionList)
        questionRecyclerView.adapter = questionAdapter

        return binding.root
    }

    // Function to animate the ProgressBar
    private fun animateProgressBar(targetProgress: Int) {
        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                if (progressCounter <= targetProgress) {
                    scoreProgressBar.progress = progressCounter // Update progress bar
                    progressCounter += 1 // Increment by 1
                    Handler(Looper.getMainLooper()).postDelayed(this, 10) // Slower animation with 20ms delay
                } else {
                    Handler(Looper.getMainLooper()).removeCallbacks(this) // Stop the animation when done
                }
            }
        }, 20) // Start with 20ms delay for smoother animation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Nullify the binding to prevent memory leaks
        Handler(Looper.getMainLooper()).removeCallbacksAndMessages(null) // Clean up handlers
    }
}

