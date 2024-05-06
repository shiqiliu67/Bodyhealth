package com.cs411cmp003.bodywatchfrontend.ui.menuHealth

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
import com.cs411cmp003.bodywatchfrontend.databinding.FragmentHealthBinding
import com.cs411cmp003.bodywatchfrontend.ui.adapter.GoalAdapter
import com.cs411cmp003.bodywatchfrontend.ui.adapter.HealthAdapter
import com.cs411cmp003.bodywatchfrontend.ui.menuGoal.GoalDetailActivity
import com.cs411cmp003.bodywatchfrontend.ui.menuGoal.GoalFragment
import com.cs411cmp003.bodywatchfrontend.ui.menuUserInfo.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthFragment : Fragment() {
    private var _binding: FragmentHealthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: HealthViewModel by viewModels()
    private lateinit var madapter: HealthAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHealthBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }
    /**
     * Init recycler view
     */
    private fun initView(){
        madapter = HealthAdapter()
        binding.rvHealth.adapter = madapter
        binding.rvHealth.layoutManager = LinearLayoutManager(requireContext())
        setUpObserver()
        binding.btnHealthAdd.setOnClickListener {
            getUserId()?.let {
                if (it != -1){
                    val intent: Intent = Intent(binding.root.context, HealthDetailActivity::class.java)
                    intent.putExtra("userid", it)
                    binding.root.context.startActivity(intent)
                }
                else{
                    Toast.makeText(requireContext(), "No user", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setUpObserver(){
        try {
            viewModel.successGetHealthResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    it.sortByDescending { it.date }
                    madapter.setData(it)
                    Log.e(TAG, "setUpObserve: health list:$it ", )
                }
            }
            viewModel.failedGetHealthResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            getUserId()?.let {
                if (it != -1){
                    viewModel.getHealthById(it)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private const val TAG = "HealthFrg"
    }
}