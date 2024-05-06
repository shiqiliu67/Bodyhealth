package com.cs411cmp003.bodywatchfrontend.ui.menuActivity

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
import com.cs411cmp003.bodywatchfrontend.data.response.GoalResponse
import com.cs411cmp003.bodywatchfrontend.data.response.UserResponse
import com.cs411cmp003.bodywatchfrontend.databinding.FragmentActivitiesBinding
import com.cs411cmp003.bodywatchfrontend.ui.adapter.ActivityAdapter
import com.cs411cmp003.bodywatchfrontend.ui.adapter.GoalAdapter
import com.cs411cmp003.bodywatchfrontend.ui.menuGoal.GoalDetailActivity
import com.cs411cmp003.bodywatchfrontend.ui.menuGoal.GoalFragment
import com.cs411cmp003.bodywatchfrontend.ui.menuGoal.GoalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitiesFragment : Fragment() {
    private var _binding: FragmentActivitiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ActivityViewModel by viewModels()
    private lateinit var madapter: ActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }
    /**
     * Init recycler view
     */
    private fun initView(){
        madapter = ActivityAdapter()
        binding.rvActivity.adapter = madapter
        binding.rvActivity.layoutManager = LinearLayoutManager(requireContext())
        setUpObserve()
        binding.btnActivityAdd.setOnClickListener {
            getUserId()?.let {
                if (it != -1){
                    val intent: Intent = Intent(binding.root.context, ActiviesActivity::class.java)
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
            viewModel.successGetActivityResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    it.sortByDescending { it.date }
                    madapter.setData(it)
                }
            }
            viewModel.failedGetActivityResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            getUserId()?.let {
                if (it != -1){
                    viewModel.getActivityById(it)
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
    }
}