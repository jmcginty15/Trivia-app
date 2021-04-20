package com.example.trivia.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.trivia.R
import com.example.trivia.databinding.CompleteGameFragmentBinding
import com.example.trivia.ui.MainViewModel

class CompleteGameFragment : Fragment() {
    private lateinit var binding: CompleteGameFragmentBinding
    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CompleteGameFragmentBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val game = mViewModel.currentGame.value!!

        if (game.questionList.size == 1) binding.questionNumber.text =
            resources.getString(R.string.questions_singular)
        else binding.questionNumber.text =
            resources.getString(R.string.questions_plural, game.questionList.size.toString())

        binding.categoryInfo.text = mViewModel.categoryName
        binding.difficultyInfo.text = mViewModel.difficultyName
        binding.typeInfo.text = mViewModel.typeName
        binding.finalScore.text =
            resources.getString(R.string.final_score, game.currentScore.toString())
        binding.newGameButton.setOnClickListener { newGame() }
    }

    private fun newGame() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_complete_game_fragment_pop)
    }
}