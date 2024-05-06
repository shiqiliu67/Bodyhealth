package com.cs411cmp003.bodywatchfrontend.ui.menuHealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.cs411cmp003.bodywatchfrontend.data.response.GoalResponse
import com.cs411cmp003.bodywatchfrontend.data.response.HealthResponse
import com.cs411cmp003.bodywatchfrontend.data.response.UserResponse
import com.cs411cmp003.bodywatchfrontend.databinding.ActivityHealthDetailBinding
import com.cs411cmp003.bodywatchfrontend.ui.menuGoal.GoalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class HealthDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealthDetailBinding
    var health: HealthResponse? = null
    var userIdIntent by Delegates.notNull<Int>()
    private val viewModel: HealthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthDetailBinding.inflate(layoutInflater)
        health = intent.getSerializableExtra("health") as HealthResponse?
        userIdIntent = intent.getIntExtra("userid", -1)
        setContentView(binding.root)
        initToolbar()
        initView(health)
    }
    private fun initView(health: HealthResponse?){
        if (userIdIntent != -1){
            //add fun
            binding.etUserId.text = userIdIntent.toString()
            binding.etDate.setText("")
            binding.etCalories.setText("")
            binding.etStep.setText("")
            binding.etAvgHeartRate.setText("")
            binding.etDate.visibility = View.VISIBLE
            binding.tvDate.visibility = View.GONE
            binding.btnUserInfoSave.setOnClickListener {
                createHealth(health = getHealthInfo("create") )
            }
        }
        else{
            //display info and save & delete fun
            health?.let {
                binding.etUserId.text = health.userId.toString()
                binding.etDate.visibility = View.GONE
                binding.tvDate.visibility = View.VISIBLE
                binding.tvDate.text = health.date
                binding.etCalories.setText(health.calories_burned.toString())
                binding.etStep.setText(health.steps.toString())
                binding.etAvgHeartRate.setText(health.avgHeartRate.toString())
                //update
                binding.btnUserInfoSave.setOnClickListener {
                    updateHealth( health = getHealthInfo("update") )
                }
                //delete
                binding.btnUserInfoDelete.setOnClickListener {
                    deleteHealth( health = getHealthInfo("update") )
                }
            }
        }
    }
    /**
     * delete health
     */
    private fun deleteHealth(health: HealthResponse){
        try{
            viewModel.successDeleteHealthResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, "Delete successful", Toast.LENGTH_SHORT).show()
                    onSupportNavigateUp()
                }
            }
            viewModel.failedDeleteHealthResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.deleteHealth(health)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
    /**
     * update health
     */
    private fun updateHealth( health: HealthResponse){
        try {
            viewModel.successUpdateHealthResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, "Update successful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.failedUpdateHealthResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.updateHealth( health = health)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * create health
     */
    private fun createHealth(health: HealthResponse){
        try {
            viewModel.successCreateHealthResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, "Create successful, You beat $it% users!", Toast.LENGTH_SHORT)
                        .show()

                }
            }
            viewModel.failedCreateHealthResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.createHealth(health= health)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * get health from input
     */
    private fun getHealthInfo(method: String): HealthResponse {
        val userResponse = getUserResponse(binding.etUserId.text.toString().toInt())
        when(method){
            "create" ->{
                return HealthResponse(
                    userId = binding.etUserId.text.toString().toInt(),
                    date = binding.etDate.text.toString(),
                    calories_burned = binding.etCalories.text.toString().toInt(),
                    steps = binding.etStep.text.toString().toInt(),
                    avgHeartRate = binding.etAvgHeartRate.text.toString().toInt(),
                    user = userResponse
                )
            }
            "update" ->{
                return HealthResponse(
                    userId = binding.etUserId.text.toString().toInt(),
                    date = binding.tvDate.text.toString(),
                    calories_burned = binding.etCalories.text.toString().toInt(),
                    steps = binding.etStep.text.toString().toInt(),
                    avgHeartRate = binding.etAvgHeartRate.text.toString().toInt(),
                    user = userResponse
                )
            }
            else ->{
                return HealthResponse(
                    userId = binding.etUserId.text.toString().toInt(),
                    date = binding.etDate.text.toString(),
                    calories_burned = binding.etCalories.text.toString().toInt(),
                    steps = binding.etStep.text.toString().toInt(),
                    avgHeartRate = binding.etAvgHeartRate.text.toString().toInt(),
                    user = userResponse
                )
            }
        }

    }

    /**
     * get user by id
     */
    private fun getUserResponse(userId: Int): UserResponse? {
        var user: UserResponse? = null
        try {
            viewModel.successGetUserResponse.observe(this) {
                if (it != null) {
                    user = it
                }
            }
            viewModel.failedGetUserResponse.observe(this) {
            }
            viewModel.getUserById(userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return user
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }
}