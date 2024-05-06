package com.cs411cmp003.bodywatchfrontend.ui.menuFood

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.cs411cmp003.bodywatchfrontend.databinding.FragmentFoodBinding
import com.cs411cmp003.bodywatchfrontend.ui.adapter.FoodAdapter
import com.cs411cmp003.bodywatchfrontend.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var madapter: FoodAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        initView()
        return binding.root

    }

    /**
     * Init recycler view
     */
    private fun initView() {
        //viewModel.clearData()
        madapter = FoodAdapter()
        binding.rvFood.adapter = madapter
        binding.rvFood.layoutManager = GridLayoutManager(requireContext(), 2)
        setUpObserve()
        Log.e(TAG, "initView: mlist is initilaized")
        var searchKey = ""
        // perform set on query text listener event
        binding.simpleSearchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener  {
            override fun onQueryTextSubmit(query: String?): Boolean {
            // do something on text submit
                searchKey = query.toString()
                if(searchKey.isNotEmpty()){
                    searchFoodByName(searchKey)
                }
                else{
                    Toast.makeText(requireContext(), "Search Item cannot be empty", Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
            // do something when text changes
                return false
            }
        })
    }

    /**
     * get data from api
     */
    private fun setUpObserve() {
        try {
            viewModel.successFoodResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    //as ArrayList<FoodResponse>
                    it.sortByDescending { it.imageURL }
                    madapter.setData(it)
                }
            }
            viewModel.failedFoodResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.getAllFood()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun searchFoodByName(name: String) {
        try {
            viewModel.successSearchResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_LONG).show()
                    Log.e(TAG, "searchResult: $it ")
                }
            }
            viewModel.failedSearchResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
            viewModel.getFoodByName(name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.rvFood.setOnClickListener {
//            findNavController().navigate(R.id.FoodDetailFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "FoodData"
    }
}
