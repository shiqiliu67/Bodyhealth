package com.cs411cmp003.bodywatchfrontend.ui.menuGoal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.cs411cmp003.bodywatchfrontend.data.response.GoalResponse
import com.cs411cmp003.bodywatchfrontend.data.response.UserResponse
import com.cs411cmp003.bodywatchfrontend.databinding.ActivityGoalDetailBinding
import com.cs411cmp003.bodywatchfrontend.ui.menuUserInfo.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class GoalDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoalDetailBinding
    var goal: GoalResponse? = null
    var userIdIntent by Delegates.notNull<Int>()
    private val viewModel: GoalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalDetailBinding.inflate(layoutInflater)
        goal = intent.getSerializableExtra("goal") as GoalResponse?
        userIdIntent = intent.getIntExtra("userid", -1)
        setContentView(binding.root)
        initToolbar()
        initView(goal)
    }

    private fun initView(goal: GoalResponse?){
        if (userIdIntent != -1){
            //add fun
            binding.etUserId.text = userIdIntent.toString()
            binding.etTimeline.setText("")
            binding.etCaloriesGoal.setText("")
            binding.etStepGoal.setText("")
            binding.etWeightGoal.setText("")
            binding.etProteinGoal.setText("")
            binding.etCarbGoal.setText("")
            binding.etFatGoal.setText("")
            binding.btnUserInfoSave.setOnClickListener {
                createGoal(goal = getGoalInfo() )
            }
        }
        else{
            //display info and save & delete fun
            goal?.let {
                binding.etUserId.text = goal.userId.toString()
                binding.etTimeline.setText(goal.timeline)
                binding.etCaloriesGoal.setText(goal.caloriesGoal.toString())
                binding.etStepGoal.setText(goal.stepsGoal.toString())
                binding.etWeightGoal.setText(goal.weightGoal.toString())
                binding.etProteinGoal.setText(goal.proteinGoal.toString())
                binding.etCarbGoal.setText(goal.carbGoal.toString())
                binding.etFatGoal.setText(goal.fatGoal.toString())
                //update
                binding.btnUserInfoSave.setOnClickListener {
                    val userId = binding.etUserId.text.toString().toInt()
                    updateGoal(userId = userId, goal = getGoalInfo() )
                }
                //delete
                binding.btnUserInfoDelete.setOnClickListener {
                    val userId = binding.etUserId.text.toString().toInt()
                    val timeline = binding.etTimeline.text.toString()
                    deleteGoal(userId = userId,timeline= timeline)
                }
            }
        }
    }

    /**
     * create goal
     */
    private fun createGoal(goal: GoalResponse){
        try {
            viewModel.successCreateGoalResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, "Update successful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.failedCreateGoalResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.createGoal(goal = goal)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * delete goal
     */
    private fun deleteGoal(userId: Int, timeline: String){
        try{
            viewModel.successDeleteGoalResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, "Delete successful", Toast.LENGTH_SHORT).show()
                    onSupportNavigateUp()
                }
            }
            viewModel.failedDeleteGoalResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.deleteGoal(userId,timeline)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * update goal
     */
    private fun updateGoal(userId: Int, goal: GoalResponse){
        try {
            viewModel.successUpdateGoalResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, "Update successful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.failedUpdateGoalResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.updateGoal(userId = userId, goal = goal)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * get goal from input
     */
    private fun getGoalInfo(): GoalResponse {
        val userResponse = getUserResponse(binding.etUserId.text.toString().toInt())
        return GoalResponse(
            userId = binding.etUserId.text.toString().toInt(),
            caloriesGoal = binding.etCaloriesGoal.text.toString().toInt(),
            carbGoal = binding.etCarbGoal.text.toString().toInt(),
            stepsGoal = binding.etStepGoal.text.toString().toInt(),
            weightGoal = binding.etWeightGoal.text.toString().toInt(),
            proteinGoal = binding.etProteinGoal.text.toString().toInt(),
            fatGoal = binding.etFatGoal.text.toString().toInt(),
            timeline = binding.etTimeline.text.toString(),
            user = userResponse
        )
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