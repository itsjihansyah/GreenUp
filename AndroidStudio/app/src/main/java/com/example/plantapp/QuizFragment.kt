package com.example.plantapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.plantapp.databinding.FragmentMyPlantDetailBinding
import com.example.plantapp.databinding.FragmentQuizBinding
import com.example.plantapp.models.Quiz
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private lateinit var questionList: List<Quiz>
    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private lateinit var database: DatabaseReference
    private val userAnswers = mutableListOf<Pair<String, Boolean>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference.child("UserAnswers")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)
        loadQuestions()
        displayQuestion(view)

        val binding = FragmentQuizBinding.inflate(inflater, container, false)


        // Handle click on left_icon
        val leftIcon = binding.root.findViewById<ImageView>(R.id.left_icon)
        leftIcon.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_quizMenuFragment)
        }


        val options = listOf(
            view.findViewById<Button>(R.id.optionA),
            view.findViewById<Button>(R.id.optionB),
            view.findViewById<Button>(R.id.optionC),
            view.findViewById<Button>(R.id.optionD)
        )

        options.forEach { button ->
            button.setOnClickListener {
                checkAnswer(button, options, view)
            }
        }
        return view
    }

    private fun loadQuestions() {
        questionList = listOf(
            Quiz(
                "What is the name of this plant?",
                "Pothos", "Snake Plant", "Monstera", "Philodendron",
                "Monstera",
                ""
            ),
            Quiz(
                "Why do Monstera plants have holes in their leaves?",
                "To capture more sunlight", "To resist strong winds", "To reduce water loss", "For decoration",
                "To resist strong winds",
                ""
            ),
            Quiz(
                "What is the common nickname for Monstera deliciosa?",
                "Swiss Cheese Plant", "Tropical Vine", "Rainforest King", "Leaf Giant",
                "Swiss Cheese Plant",
                ""
            ),
            Quiz(
                "Where is the Monstera plant naturally found?",
                "Africa", "Australia", "Southeast Asia", "Central America",
                "Central America",
                ""
            )
        )
    }

    private fun displayQuestion(view: View) {
        val question = questionList[currentQuestionIndex]
        view.findViewById<TextView>(R.id.questionNumber).text = "Question ${currentQuestionIndex + 1}"
        view.findViewById<TextView>(R.id.questionText).text = question.questionText
        view.findViewById<TextView>(R.id.questionProgress).text = "${currentQuestionIndex + 1}/${questionList.size}"

        val options = listOf(
            view.findViewById<Button>(R.id.optionA),
            view.findViewById<Button>(R.id.optionB),
            view.findViewById<Button>(R.id.optionC),
            view.findViewById<Button>(R.id.optionD)
        )

        options[0].text = question.optionA
        options[1].text = question.optionB
        options[2].text = question.optionC
        options[3].text = question.optionD

        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = ((currentQuestionIndex + 1) * 100) / questionList.size
    }

    private fun checkAnswer(selectedButton: Button, options: List<Button>, view: View) {
        val question = questionList[currentQuestionIndex]
        val isCorrect = selectedButton.text == question.correctAnswer

        options.forEach { button ->
            button.setBackgroundColor(
                if (button.text == question.correctAnswer)
                    Color.parseColor("#428D74") // Correct answer background (Greenish)
                else
                    Color.parseColor("#CC3333") // Incorrect answer background (Reddish)
            )
            button.setTextColor(Color.WHITE) // Set text color to white
        }

        userAnswers.add(Pair(question.questionText, isCorrect))
        if (isCorrect) correctAnswers++

        Handler(Looper.getMainLooper()).postDelayed({
            currentQuestionIndex++
            if (currentQuestionIndex < questionList.size) {
                resetOptions(options)
                displayQuestion(view)
            } else {
                saveResultsToFirebase()
                showResults()
            }
        }, 1000)
    }

    private fun resetOptions(options: List<Button>) {
        options.forEach { button ->
            button.setBackgroundColor(Color.parseColor("#F5F7FB"))
            button.setTextColor(Color.BLACK)
        }
    }

    private fun saveResultsToFirebase() {
        val userData = HashMap<String, Any>()
        userData["totalCorrectAnswers"] = correctAnswers
        userData["totalQuestions"] = questionList.size

        userAnswers.forEachIndexed { index, pair ->
            userData["Question${index + 1}"] = mapOf(
                "question" to pair.first,
                "isCorrect" to pair.second
            )
        }

        val userId = "1234"
        database.child(userId).setValue(userData)
    }

    private fun showResults() {
        val bundle = Bundle().apply {
            putInt("correctAnswer", correctAnswers)
        }
        findNavController().navigate(R.id.action_quizFragment_to_quizResultFragment, bundle)
    }
}
