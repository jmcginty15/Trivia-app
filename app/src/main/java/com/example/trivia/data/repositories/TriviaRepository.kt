package com.example.trivia.data.repositories

import com.example.trivia.data.models.ResultsDTO
import com.example.trivia.data.remote.network.TriviaManager
import io.reactivex.Single

class TriviaRepository {
    fun getQuestions(
        amount: String,
        category: String?,
        difficulty: String?,
        type: String?
    ) : Single<ResultsDTO> {
        return TriviaManager().getQuestions(amount, category, difficulty, type)
    }
}