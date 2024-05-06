package com.cs411cmp003.bodywatchfrontend.ui.menuFood

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cs411cmp003.bodywatchfrontend.data.response.FoodResponse
import com.cs411cmp003.bodywatchfrontend.databinding.ActivityFoodDetailBinding

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val food = intent.getSerializableExtra("food") as FoodResponse
        initToolbar()
        initView(food)

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initView(food: FoodResponse ) {
        Log.e("TAG", "initView: food:$food")
        binding.etFoodId.text = food.foodId.toString()
        binding.etFoodName.text = food.prodName
        binding.etGenericName.text = food.genericName
        binding.etQuantity.text = food.quantity
        binding.etServSize.text = food.servSize
        binding.etEnergy.text = food.energy100g.toString()
        binding.etEnergyFat.text = food.energyFat100g.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }

}