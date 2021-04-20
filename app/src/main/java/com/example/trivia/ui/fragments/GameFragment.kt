package com.example.trivia.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.trivia.R
import com.example.trivia.databinding.GameFragmentBinding
import com.example.trivia.ui.GameModel
import com.example.trivia.ui.MainViewModel
import java.util.*

class GameFragment : Fragment() {
    private lateinit var binding: GameFragmentBinding
    private val mViewModel: MainViewModel by activityViewModels()
    private var isCorrect = false
    private var answerChecked = false
    private var selectedIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.currentGame.observe(viewLifecycleOwner, { game ->
            if (game != null && !answerChecked) showQuestion(game.currentQuestion, game)
        })

        binding.answers.setOnCheckedChangeListener { group, checkedId ->
            selectedIndex = checkedId
            isCorrect = checkedId == 100
            setButtonClickable(true)
        }
        binding.answerButton.setOnClickListener { checkAnswerOrNextQuestion() }
        binding.categoryInfo.text = mViewModel.categoryName
        binding.difficultyInfo.text = mViewModel.difficultyName
        binding.typeInfo.text = mViewModel.typeName
        binding.score.text = resources.getString(R.string.score, 0.toString())
    }

    @SuppressLint("ResourceType")
    private fun showQuestion(questionNum: Int, game: GameModel) {
        val question = game.questionList[questionNum - 1]

        binding.title.text = resources.getString(
            R.string.question_title,
            questionNum.toString(),
            game.questionList.size.toString()
        )
        binding.category.text = question.category
        binding.difficulty.text = question.difficulty.capitalize(Locale.ROOT)
        binding.question.text = question.question
        binding.correctIncorrect.text = resources.getString(R.string.empty)

        setButtonClickable(false)

        if (question.type == "multiple") {
            val answers = mutableListOf<String>()
            answers.add(question.correctAnswer)
            for (answer in question.incorrectAnswers) answers.add(answer)
            answers.shuffle()

            val correctIndex = findCorrectIndex(answers, question.correctAnswer)
            for ((index, answer) in answers.withIndex()) {
                val answerButton = RadioButton(requireContext())
                answerButton.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                answerButton.text = answer
                if (index == correctIndex) answerButton.id = 100
                else answerButton.id = index
                binding.answers.addView(answerButton)
            }
        } else {
            val trueButton = RadioButton(requireContext())
            trueButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            trueButton.text = resources.getString(R.string.true_answer)
            val falseButton = RadioButton(requireContext())
            falseButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            falseButton.text = resources.getString(R.string.false_answer)

            if (question.correctAnswer == "True") {
                trueButton.id = 100
                falseButton.id = 0
            } else {
                trueButton.id = 0
                falseButton.id = 100
            }

            binding.answers.addView(trueButton)
            binding.answers.addView(falseButton)
        }
    }

    private fun findCorrectIndex(answers: List<String>, correctAnswer: String): Int {
        for ((index, answer) in answers.withIndex()) if (answer == correctAnswer) return index
        return 100
    }

    private fun setButtonClickable(clickable: Boolean) {
        val color = if (clickable) R.color.design_default_color_primary else R.color.grey

        binding.answerButton.isClickable = clickable
        binding.answerButton.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                color
            )
        )
    }

    private fun checkAnswerOrNextQuestion() {
        if (answerChecked) {

        } else {
            checkAnswer()
        }
    }

    private fun nextQuestion() {
        answerChecked = false
    }

    private fun checkAnswer() {
        answerChecked = true
        val score = mViewModel.currentGame.value?.currentScore
        val question = mViewModel.currentGame.value?.currentQuestion

        if (isCorrect) {
            binding.correctIncorrect.text = resources.getString(R.string.correct)
            val newScore = score!!.plus(1)
            mViewModel.currentGame.value?.currentScore = newScore
            binding.score.text = resources.getString(R.string.score, newScore.toString())
        } else {
            binding.correctIncorrect.text = resources.getString(R.string.incorrect)
            for (child in binding.answers.children) {
                if (child.id == selectedIndex) child.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
            }
        }

        for (child in binding.answers.children) {
            if (child.id == 100) child.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        mViewModel.currentGame.value?.currentQuestion = question!!.plus(1)

        binding.answerButton.text = resources.getString(R.string.next_question)
        setButtonClickable(true)
    }
}