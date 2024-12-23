package com.example.plantapp.repository
import com.example.plantapp.models.Quiz

object QuestionRepository {
    val questionList = listOf(
        Quiz(
            "What is the name of this plant?",
            optionA = "Pothos",
            optionB = "Snake Plant",
            optionC = "Monstera",
            optionD = "Philodendron",
            correctAnswer = "Monstera",
            explanation = "Monstera is a tropical plant known for its large, split leaves. It's often called the 'Swiss Cheese Plant'."
        ),
        Quiz(
            "Why do Monstera plants have holes in their leaves?",
            optionA = "To capture more sunlight",
            optionB = "To resist strong winds",
            optionC = "To reduce water loss",
            optionD = "For decoration",
            correctAnswer = "To resist strong winds",
            explanation = "The holes, or fenestrations, help the plant resist strong winds and heavy rainfall in its native rainforest habitat."
        ),
        Quiz(
            "What is the common nickname for Monstera deliciosa?",
            optionA = "Swiss Cheese Plant",
            optionB = "Tropical Vine",
            optionC = "Rainforest King",
            optionD = "Leaf Giant",
            correctAnswer = "Swiss Cheese Plant",
            explanation = "Monstera deliciosa is nicknamed the 'Swiss Cheese Plant' because of the holes and splits in its leaves."
        ),
        Quiz(
            "Where is the Monstera plant naturally found?",
            optionA = "Africa",
            optionB = "Australia",
            optionC = "Southeast Asia",
            optionD = "Central America",
            correctAnswer = "Central America",
            explanation = "Monstera plants are native to the tropical rainforests of Central America, particularly Mexico and Panama."
        ),
        Quiz(
            "What type of plant is Monstera?",
            optionA = "Succulent",
            optionB = "Epiphyte",
            optionC = "Fern",
            optionD = "Herbaceous",
            correctAnswer = "Epiphyte",
            explanation = "Monstera is an epiphyte, meaning it grows on other plants or surfaces, absorbing moisture and nutrients from the air."
        ),
        Quiz(
            "How often should Monstera be watered?",
            optionA = "Once a day",
            optionB = "Every week",
            optionC = "Every 2-4 weeks",
            optionD = "Once a month",
            correctAnswer = "Every week",
            explanation = "Monsteras prefer consistent watering about once a week, allowing the topsoil to dry out slightly between waterings."
        ),
        Quiz(
            "Which nutrient promotes Monstera leaf growth?",
            optionA = "Potassium",
            optionB = "Nitrogen",
            optionC = "Phosphorus",
            optionD = "Calcium",
            correctAnswer = "Nitrogen",
            explanation = "Nitrogen is essential for promoting leafy growth and keeping Monstera leaves vibrant and healthy."
        ),
        Quiz(
            "Why does a Monstera plant's leaves turn yellow?",
            optionA = "Too much sunlight",
            optionB = "Too much water",
            optionC = "Nutrient deficiency",
            optionD = "Cold temperatures",
            correctAnswer = "Too much water",
            explanation = "Yellow leaves often indicate overwatering, which can lead to root rot. Ensure proper drainage and allow soil to dry slightly."
        ),
        Quiz(
            "What is Monstera's role in its ecosystem?",
            optionA = "Provides shade for smaller plants",
            optionB = "Absorbs nutrients from host plants",
            optionC = "Acts as a habitat for animals",
            optionD = "Supplies oxygen",
            correctAnswer = "Acts as a habitat for animals",
            explanation = "Monstera plants provide shelter for small animals and insects in their natural rainforest ecosystems."
        ),
        Quiz(
            "What does 'deliciosa' refer to in Monstera deliciosa?",
            optionA = "Its delicious fruits",
            optionB = "Its vibrant green color",
            optionC = "Its tropical beauty",
            optionD = "Its large size",
            correctAnswer = "Its delicious fruits",
            explanation = "The name 'deliciosa' refers to the plant's edible fruit, which has a sweet, tropical flavor similar to pineapple and banana."
        )
    )
}
