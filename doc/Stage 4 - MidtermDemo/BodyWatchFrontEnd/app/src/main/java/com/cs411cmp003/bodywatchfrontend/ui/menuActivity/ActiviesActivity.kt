package com.cs411cmp003.bodywatchfrontend.ui.menuActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.cs411cmp003.bodywatchfrontend.data.response.ActivityResponse
import com.cs411cmp003.bodywatchfrontend.databinding.ActivityActiviesDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ActiviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActiviesDetailBinding
    var item: ActivityResponse? = null
    var userIdIntent by Delegates.notNull<Int>()
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActiviesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        item = intent.getSerializableExtra("activity") as ActivityResponse?
        userIdIntent = intent.getIntExtra("userid", -1)
        initToolbar()
        initView(item)
    }

    private fun initView(item: ActivityResponse?){
        if (userIdIntent != -1){
            //add fun
            binding.etUserId.text = userIdIntent.toString()
            binding.etStartTime.setText("")
            binding.etEndTime.setText("")
            binding.etDate.setText("")
            binding.etCaloriesBurned.setText("")
            binding.etStep.setText("")
            binding.etAvgHeartRate.setText("")
            binding.etExercise.setText("")
            binding.btnUserInfoSave.setOnClickListener {
                createActivity(activity = getActivityInfo() )
            }
        }
        else{
            //display info and save & delete fun
            item?.let {
                binding.etUserId.text = item.userId.toString()
                binding.etStartTime.setText(item.startTime)
                binding.etEndTime.setText(item.endTime.toString())
                binding.etDate.setText(item.date)
                binding.etCaloriesBurned.setText(item.caloriesBurned.toString())
                binding.etStep.setText(item.steps.toString())
                binding.etAvgHeartRate.setText(item.avgHeartRate.toString())
                binding.etExercise.setText(item.exercise)
                //update
                binding.btnUserInfoSave.setOnClickListener {
                    val userId = binding.etUserId.text.toString().toInt()
                    val exercise = binding.etExercise.text.toString()
                    val startTime = binding.etStartTime.text.toString()
                    updateActivity(userId = userId, activity = getActivityInfo(), exercise = exercise, startTime = startTime  )
                }
                //delete
                binding.btnUserInfoDelete.setOnClickListener {
                    val userId = binding.etUserId.text.toString().toInt()
                    val exercise = binding.etExercise.text.toString()
                    val startTime = binding.etStartTime.text.toString()
                    deleteActivity(userId = userId, exercise = exercise, startTime = startTime)
                }
            }
        }
    }
    /**
     * Update Activity
     */
    private fun updateActivity(activity: ActivityResponse, userId: Int, exercise: String, startTime:String){
        try {
            viewModel.successUpdateActivityResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, "Update successful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.failedUpdateActivityResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.updateActivity(activity= activity, userId = userId, exercise = exercise, startTime = startTime)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * Delete Activity
     */
    private fun deleteActivity(userId: Int, exercise: String, startTime:String){
        try{
            viewModel.successDeleteActivityResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, "Delete successful", Toast.LENGTH_SHORT).show()
                    onSupportNavigateUp()
                }
            }
            viewModel.failedDeleteActivityResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.deleteActivity(userId, exercise, startTime)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * Create Activity
     */
    private fun createActivity(activity: ActivityResponse){
        try {
            viewModel.successCreateActivityResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, "Update successful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.failedCreateActivityResponse.observe(this) {
                if (it != null) {
                    Toast.makeText(baseContext, it, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            viewModel.createActivity(activity = activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * get goal from input
     */
    private fun getActivityInfo(): ActivityResponse {
        return ActivityResponse(
            userId = binding.etUserId.text.toString().toInt(),
            avgHeartRate = binding.etAvgHeartRate.text.toString().toInt(),
            date = binding.etDate.text.toString(),
            steps = binding.etStep.text.toString().toInt(),
            startTime = binding.etStartTime.text.toString(),
            endTime = binding.etEndTime.text.toString(),
            caloriesBurned = binding.etCaloriesBurned.text.toString().toInt(),
            exercise = binding.etExercise.text.toString(),
            user = null
        )
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