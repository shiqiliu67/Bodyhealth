package com.cs411cmp003.bodywatchfrontend.ui.menuGoal

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs411cmp003.bodywatchfrontend.data.repository.ApiRepository
import com.cs411cmp003.bodywatchfrontend.data.response.GoalResponse
import com.cs411cmp003.bodywatchfrontend.data.response.UserResponse
import com.cs411cmp003.bodywatchfrontend.ui.menuUserInfo.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    val successCreateGoalResponse = MutableLiveData<String>()
    val failedCreateGoalResponse = MutableLiveData<String?>()
    val successGetGoalResponse = MutableLiveData<ArrayList<GoalResponse>>()
    val failedGetGoalResponse = MutableLiveData<String?>()
    val successUpdateGoalResponse = MutableLiveData<String>()
    val failedUpdateGoalResponse = MutableLiveData<String?>()
    val successDeleteGoalResponse = MutableLiveData<String>()
    val failedDeleteGoalResponse = MutableLiveData<String?>()
    val successGetUserResponse = MutableLiveData<UserResponse>()
    val failedGetUserResponse = MutableLiveData<String?>()
    /**
     * create goal as Json
     */
    fun createGoal(goal: GoalResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.createGoal(goal)
                if (response.isSuccessful) {
                    successCreateGoalResponse.postValue(response.body())
                    Log.e(TAG, "create successful: ${response.body()}")
                } else {
                    failedCreateGoalResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedCreateGoalResponse.postValue("failed.")
            }
        }
    }

    /**
     * Get Goals By id
     */

    fun getGoalById(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getGoalById(userId = userId)
                if (response.isSuccessful) {
                    successGetGoalResponse.postValue(response.body())
                    Log.e(TAG, "goals: ${response.body()}")
                } else {
                    failedGetGoalResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedGetGoalResponse.postValue("failed.")
            }
        }
    }

    /**
     * update Goal by id
     */
    fun updateGoal(userId: Int, goal: GoalResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.updateGoal(userId = userId, goal = goal)
                if (response.isSuccessful) {
                    successUpdateGoalResponse.postValue(response.body())
                    Log.e(TAG, "update successful: ${response.body()}")
                } else {
                    failedUpdateGoalResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedUpdateGoalResponse.postValue("failed.")
            }
        }
    }

    /**
     * Delete Goal by id
     */
    fun deleteGoal(userId: Int, timeline: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.deleteGoal(userId = userId, timeline= timeline)
                if (response.isSuccessful) {
                    successDeleteGoalResponse.postValue(response.body())
                    Log.e(TAG, "delete successful: ${response.body()}")
                } else {
                    failedDeleteGoalResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedDeleteGoalResponse.postValue("failed.")
            }
        }
    }
    /**
     * Get User By id
     */

    fun getUserById(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getUserById(userId = userId)
                if (response.isSuccessful) {
                    successGetUserResponse.postValue(response.body())
                    Log.e(UserViewModel.TAG, "user: ${response.body()}")
                } else {
                    failedGetUserResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedGetUserResponse.postValue("failed.")
            }
        }
    }

    companion object {
        const val TAG = "GoalViewModel"
    }
}