package com.example.trivia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.trivia.databinding.GameFragmentBinding
import com.example.trivia.ui.MainViewModel

class GameFragment : Fragment() {
    private lateinit var binding: GameFragmentBinding
    private val mViewModel: MainViewModel by activityViewModels()

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
            binding.category.text = mViewModel.category.toString()
            binding.difficulty.text = mViewModel.difficulty.toString()
            binding.type.text = mViewModel.type.toString()
        })
    }
}