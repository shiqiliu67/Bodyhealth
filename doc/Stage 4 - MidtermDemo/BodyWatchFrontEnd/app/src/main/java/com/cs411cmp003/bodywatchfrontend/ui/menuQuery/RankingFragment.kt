package com.cs411cmp003.bodywatchfrontend.ui.menuQuery

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.cs411cmp003.bodywatchfrontend.R
import com.cs411cmp003.bodywatchfrontend.databinding.FragmentAdvancedQueryBinding
import com.cs411cmp003.bodywatchfrontend.databinding.FragmentRankingBinding
import com.cs411cmp003.bodywatchfrontend.ui.viewmodel.MainViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment : Fragment() {
    lateinit var binding: FragmentRankingBinding
    private val viewModel: MainViewModel by viewModels()
    // on below line we are creating
    // variables for our bar chart
    lateinit var barChart: BarChart

    // on below line we are creating
    // a variable for bar data
    lateinit var barData: BarData

    // on below line we are creating a
    // variable for bar data set
    lateinit var barDataSet: BarDataSet

    // on below line we are creating array list for bar data
    lateinit var barEntriesList: ArrayList<BarEntry>

     var leadboardData: ArrayList<List<Int>> = ArrayList()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRankingBinding.inflate(layoutInflater, container, false)
        initView()
        return binding.root
    }

    private fun initView(){
        setUpObserve()
    }


    private fun setUpObserve(){
        viewModel.successLeadboardResponse.observe(viewLifecycleOwner){
            binding.tvQueryOutput.text =  it.joinToString(",")
            Log.e("TAG","query: $it")
            for (item in it){
                leadboardData.add(item)
                initChartView()
            }
        }
        viewModel.failedLeadboardResponse.observe(viewLifecycleOwner){
            binding.tvQueryOutput.text =  it
        }
        viewModel.getLeadboard()
    }

    private fun initChartView(){
        // on below line we are initializing
        // our variable with their ids.
        barChart = binding.idBarChart
        getBarChartData()
        barChart.description.isEnabled = false
        barDataSet = BarDataSet(barEntriesList, "Lead Board")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.setColor(resources.getColor(R.color.purple_200))
        barDataSet.valueTextSize = 16f

    }

    private fun getBarChartData(){
        barEntriesList = ArrayList()
        for ((i, item) in leadboardData.withIndex()){
            barEntriesList.add(BarEntry(i.toFloat(),item[1].toFloat()))
        }
    }

}