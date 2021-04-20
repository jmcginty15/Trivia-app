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

        val categoryAdapter = getAdapter(CATEGORY_MENU)
        (binding.categoryText as? AutoCompleteTextView)?.setAdapter(categoryAdapter)

        val difficultyAdapter = getAdapter(DIFFICULTY_MENU)
        (binding.difficultyText as? AutoCompleteTextView)?.setAdapter(difficultyAdapter)

        val typeAdapter = getAdapter(TYPE_MENU)
        (binding.typeText as? AutoCompleteTextView)?.setAdapter(typeAdapter)

        binding.startButton.setOnClickListener { startGame() }
    }

    private fun getMenu(menuIds: List<Int>): List<String> {
        val menuStrings = arrayListOf<String>()
        for (item in menuIds) {
            menuStrings.add(binding.root.resources.getString(item))
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