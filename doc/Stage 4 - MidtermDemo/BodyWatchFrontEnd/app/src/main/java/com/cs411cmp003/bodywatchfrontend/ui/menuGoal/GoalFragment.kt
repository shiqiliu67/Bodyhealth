package com.cs411cmp003.bodywatchfrontend.ui.menuGoal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs411cmp003.bodywatchfrontend.databinding.FragmentGoalBinding
import com.cs411cmp003.bodywatchfrontend.ui.adapter.GoalAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoalFragment : Fragment() {
    private var _binding: FragmentGoalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GoalViewModel by viewModels()

    private lateinit var madapter: GoalAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGoalBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    /**
     * Init recycler view
     */
    private fun initView(){
        madapter = GoalAdapter()
        binding.rvGoals.adapter = madapter
        binding.rvGoals.layoutManager = LinearLayoutManager(requireContext())
        setUpObserve()
        binding.btnGoalAdd.setOnClickListener {
            getUserId()?.let {
                if (it != -1){
                    val intent: Intent = Intent(binding.root.context, GoalDetailActivity::class.java)
                    intent.putExtra("userid", it)
                    binding.root.context.startActivity(intent)
                }
                else{
                    Toast.makeText(requireContext(), "No user", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * get data from api
     */
    private fun setUpObserve() {
        try {
            viewModel.successGetGoalResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    it.sortByDescending { it.timeline }
                    madapter.setData(it)
                    Log.e(TAG, "setUpObserve: goal list:$it ", )
                }
            }
            viewModel.failedGetGoalResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            getUserId()?.let {
                if (it != -1){
                    viewModel.getGoalById(it)
                }
                else{
                    Toast.makeText(requireContext(), "No user", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getUserId(): Int? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        return sharedPref?.getInt("user",-1)
    }



    companion object {
        private const val TAG = "GoalFragment"
    }
}