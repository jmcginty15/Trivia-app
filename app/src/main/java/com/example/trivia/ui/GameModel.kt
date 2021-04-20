package com.example.trivia.ui

import com.example.trivia.data.models.QuestionDTO

data class GameModel(
    var questions: Int,
    var questionList: List<QuestionDTO>,
    var category: String,
    var difficulty: String,
    var type: String,
    var currentQuestion: Int = 1,
    var currentScore: Int = 0
)