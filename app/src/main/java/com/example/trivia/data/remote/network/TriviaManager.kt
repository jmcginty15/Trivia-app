package com.example.trivia.data.remote.network

import com.example.trivia.data.models.ResultsDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

class TriviaManager {
    private val service: TriviaService
    private val retrofit = RetrofitService().providesRetrofitService()

    init {
        service = retrofit.create(TriviaService::class.java)
    }

    fun getQuestions(
        amount: String,
        category: String?,
        difficulty: String?,
        type: String?
    ) = service.getQuestions(amount, category, difficulty, type)

    interface TriviaService {
        @GET("/api.php")
        fun getQuestions(
            @Query("amount") amount: String,
            @Query("category") category: String?,
            @Query("difficulty") difficulty: String?,
            @Query("type") type: String?
        ) : Single<ResultsDTO>
    }
}