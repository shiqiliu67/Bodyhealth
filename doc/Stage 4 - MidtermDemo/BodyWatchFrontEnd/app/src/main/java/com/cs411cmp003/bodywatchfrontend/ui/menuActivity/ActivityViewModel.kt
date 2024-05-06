package com.cs411cmp003.bodywatchfrontend.ui.menuActivity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs411cmp003.bodywatchfrontend.data.repository.ApiRepository
import com.cs411cmp003.bodywatchfrontend.data.response.ActivityResponse
import com.cs411cmp003.bodywatchfrontend.data.response.UserResponse
import com.cs411cmp003.bodywatchfrontend.ui.menuUserInfo.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    val successCreateActivityResponse = MutableLiveData<String>()
    val failedCreateActivityResponse = MutableLiveData<String?>()
    val successGetActivityResponse = MutableLiveData<ArrayList<ActivityResponse>>()
    val failedGetActivityResponse = MutableLiveData<String?>()
    val successUpdateActivityResponse = MutableLiveData<String>()
    val failedUpdateActivityResponse = MutableLiveData<String?>()
    val successDeleteActivityResponse = MutableLiveData<String>()
    val failedDeleteActivityResponse = MutableLiveData<String?>()
    val successGetUserResponse = MutableLiveData<UserResponse>()
    val failedGetUserResponse = MutableLiveData<String?>()
    /**
     * create Activity
     */
    fun createActivity(activity: ActivityResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.createActivity(activity = activity)
                if (response.isSuccessful) {
                    successCreateActivityResponse.postValue(response.body())
                    Log.e(TAG, "create successful: ${response.body()}")
                } else {
                    failedCreateActivityResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedCreateActivityResponse.postValue("failed.")
            }
        }
    }

    /**
     * Get Activitys By id
     */
    fun getActivityById(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getActivityById(userId = userId)
                if (response.isSuccessful) {
                    successGetActivityResponse.postValue(response.body())
                    Log.e(TAG, "Activitys: ${response.body()}")
                } else {
                    failedGetActivityResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedGetActivityResponse.postValue("failed.")
            }
        }
    }

    /**
     * update Activity by id
     */
    fun updateActivity(activity: ActivityResponse, userId: Int, exercise: String, startTime:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.updateActivity(activity= activity, userId = userId, exercise = exercise, startTime = startTime)
                if (response.isSuccessful) {
                    successUpdateActivityResponse.postValue(response.body())
                    Log.e(TAG, "update successful: ${response.body()}")
                } else {
                    failedUpdateActivityResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedUpdateActivityResponse.postValue("failed.")
            }
        }
    }

    /**
     * Delete Activity by id
     */
    fun deleteActivity(userId: Int, exercise: String, startTime:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.deleteActivity(userId = userId, exercise = exercise, startTime = startTime)
                if (response.isSuccessful) {
                    successDeleteActivityResponse.postValue(response.body())
                    Log.e(TAG, "delete successful: ${response.body()}")
                } else {
                    failedDeleteActivityResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedDeleteActivityResponse.postValue("failed.")
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
        const val TAG = "ActivityViewModel"
    }
}