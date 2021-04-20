package com.example.trivia.ui

import android.app.Application
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trivia.data.models.ResultsDTO
import com.example.trivia.data.repositories.TriviaRepository
import com.example.trivia.ui.menus.CATEGORY_MENU
import com.example.trivia.ui.menus.DIFFICULTY_MENU
import com.example.trivia.ui.menus.TYPE_MENU
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()
    private val triviaRepository = TriviaRepository()

    var categoryMenuAdapter: ArrayAdapter<Any>? = null
    var difficultyMenuAdapter: ArrayAdapter<Any>? = null
    var typeMenuAdapter: ArrayAdapter<Any>? = null

    val currentGame: LiveData<GameModel>
        get() = _currentGame

    private val _currentGame: MutableLiveData<GameModel> by lazy {
        MutableLiveData<GameModel>()
    }

    lateinit var categoryName: String
    lateinit var difficultyName: String
    lateinit var typeName: String

    var category: String? = null
        set(value) {
            field = when (value) {
                "General Knowledge" -> "9"
                "Entertainment: Books" -> "10"
                "Entertainment: Film" -> "11"
                "Entertainment: Music" -> "12"
                "Entertainment: Musicals & Theatres" -> "13"
                "Entertainment: Television" -> "14"
                "Entertainment: Video Games" -> "15"
                "Entertainment: Board Games" -> "16"
                "Science & Nature" -> "17"
                "Science: Computers" -> "18"
                "Science: Mathematics" -> "19"
                "Mythology" -> "20"
                "Sports" -> "21"
                "Geography" -> "22"
                "History" -> "23"
                "Politics" -> "24"
                "Art" -> "25"
                "Celebrities" -> "26"
                "Animals" -> "27"
                "Vehicles" -> "28"
                "Entertainment: Comics" -> "29"
                "Science: Gadgets" -> "30"
                "Entertainment: Japanese Anime & Manga" -> "31"
                "Entertainment: Cartoon & Animations" -> "32"
                else -> null
            }
        }

    var difficulty: String? = null
        set(value) {
            field =
                if (value == "Easy" || value == "Medium" || value == "Hard") value.toLowerCase(
                    Locale.ROOT
                ) else null
        }

    var type: String? = null
        set(value) {
            field = when (value) {
                "Multiple Choice" -> "multiple"
                "True / False" -> "boolean"
                else -> null
            }
        }

    fun startGame(amount: String, category: String?, difficulty: String?, type: String?) {
        _currentGame.value = null
        disposable.add(
            triviaRepository.getQuestions(amount, category, difficulty, type)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onStartGameSuccess, this::onStartGameError)
        )
    }

    private fun onStartGameSuccess(response: ResultsDTO) {
        _currentGame.value = GameModel(
            response.results.size,
            response.results,
            categoryName,
            difficultyName,
            typeName
        )
    }

    private fun onStartGameError(e: Throwable) {

    }
}