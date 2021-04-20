package com.example.trivia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.trivia.R
import com.example.trivia.databinding.CreateGameFragmentBinding
import com.example.trivia.ui.MainViewModel
import com.example.trivia.ui.menus.*

class CreateGameFragment : Fragment() {
    private lateinit var binding: CreateGameFragmentBinding
    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateGameFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mViewModel.categoryMenuAdapter == null) mViewModel.categoryMenuAdapter =
            getAdapter(CATEGORY_MENU)
        binding.categoryText.setAdapter(mViewModel.categoryMenuAdapter)

        if (mViewModel.difficultyMenuAdapter == null) mViewModel.difficultyMenuAdapter =
            getAdapter(DIFFICULTY_MENU)
        binding.difficultyText.setAdapter(mViewModel.difficultyMenuAdapter)

        if (mViewModel.typeMenuAdapter == null) mViewModel.typeMenuAdapter = getAdapter(TYPE_MENU)
        binding.typeText.setAdapter(mViewModel.typeMenuAdapter)

        binding.startButton.setOnClickListener { startGame() }
    }

    private fun getMenu(menuIds: List<Int>): List<String> {
        val menuStrings = arrayListOf<String>()
        for (item in menuIds) {
            menuStrings.add(resources.getString(item))
        }
        return menuStrings
    }

    private fun getAdapter(menuIds: List<Int>): ArrayAdapter<Any> {
        val menu = getMenu(menuIds)
        return ArrayAdapter(requireContext(), R.layout.dropdown_item, menu)
    }

    private fun startGame() {
        val amount = binding.amountInput.editText?.text.toString()
        val category = binding.categoryInput.editText?.text.toString()
        val difficulty = binding.difficultyInput.editText?.text.toString()
        val type = binding.typeInput.editText?.text.toString()

        mViewModel.category = category
        mViewModel.categoryName = category
        mViewModel.difficulty = difficulty
        mViewModel.difficultyName = difficulty
        mViewModel.type = type
        mViewModel.typeName = type

        mViewModel.startGame(amount, mViewModel.category, mViewModel.difficulty, mViewModel.type)

        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_create_game_fragment_to_game_fragment)
    }
}