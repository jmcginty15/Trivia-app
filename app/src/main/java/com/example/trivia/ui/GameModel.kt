package com.example.trivia.ui

import com.example.trivia.data.models.QuestionDTO

class GameModel(
    questions: Int,
    questionList: List<QuestionDTO>,
    category: String,
    difficulty: String,
    type: String,
    currentQuestion: Int = 0,
    currentScore: Int = 0
) {
}