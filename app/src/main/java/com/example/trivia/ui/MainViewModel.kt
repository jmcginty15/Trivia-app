package com.example.trivia.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.trivia.data.models.QuestionDTO
import com.example.trivia.data.models.ResultsDTO
import com.example.trivia.data.repositories.TriviaRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()
    private val triviaRepository = TriviaRepository()
    private lateinit var currentGame: GameModel

    private lateinit var category: String
    private lateinit var difficulty: String
    private lateinit var type: String

    fun startGame(amount: String, category: String?, difficulty: String?, type: String?) {
        if (category != null) this.category = category else this.category = "Any Category"
        if (difficulty != null) this.difficulty = difficulty else this.difficulty = "Any Difficulty"
        if (type != null) this.type = type else this.type = "Any Type"

        disposable.add(
            triviaRepository.getQuestions(amount, category, difficulty, type)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onStartGameSuccess, this::onStartGameError)
        )
    }

    private fun onStartGameSuccess(response: ResultsDTO) {
        currentGame = GameModel(response.results.size, response.results, category, difficulty, type)
    }

    private fun onStartGameError(e: Throwable) {

    }
}