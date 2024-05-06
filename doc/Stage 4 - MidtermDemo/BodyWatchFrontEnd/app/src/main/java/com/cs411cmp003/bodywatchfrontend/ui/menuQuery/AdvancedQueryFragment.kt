package com.cs411cmp003.bodywatchfrontend.ui.menuQuery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.cs411cmp003.bodywatchfrontend.R
import com.cs411cmp003.bodywatchfrontend.databinding.FragmentAdvancedQueryBinding
import com.cs411cmp003.bodywatchfrontend.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdvancedQueryFragment : Fragment() {
    lateinit var binding: FragmentAdvancedQueryBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAdvancedQueryBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView(){
        binding.btnFirst.setOnClickListener{
            setUpObserve()
        }

    }

    private fun setUpObserve(){
        viewModel.successSuggestionResponse.observe(viewLifecycleOwner){
            binding.tvQueryOutput.text =  it.joinToString("\n")
            Log.e("TAG","query: $it")
        }
        viewModel.failedSuggestionResponse.observe(viewLifecycleOwner){
            binding.tvQueryOutput.text =  it
        }
        viewModel.getFoodSuggestion()
    }

}