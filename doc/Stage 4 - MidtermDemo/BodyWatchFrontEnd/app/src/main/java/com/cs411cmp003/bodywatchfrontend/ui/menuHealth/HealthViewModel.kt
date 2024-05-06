package com.cs411cmp003.bodywatchfrontend.ui.menuHealth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs411cmp003.bodywatchfrontend.data.repository.ApiRepository
import com.cs411cmp003.bodywatchfrontend.data.response.HealthResponse
import com.cs411cmp003.bodywatchfrontend.data.response.UserResponse
import com.cs411cmp003.bodywatchfrontend.ui.menuUserInfo.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthViewModel  @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    val successCreateHealthResponse = MutableLiveData<Int?>()
    val failedCreateHealthResponse = MutableLiveData<String?>()
    val successGetHealthResponse = MutableLiveData<ArrayList<HealthResponse>>()
    val failedGetHealthResponse = MutableLiveData<String?>()
    val successUpdateHealthResponse = MutableLiveData<String>()
    val failedUpdateHealthResponse = MutableLiveData<String?>()
    val successDeleteHealthResponse = MutableLiveData<String>()
    val failedDeleteHealthResponse = MutableLiveData<String?>()
    val successGetUserResponse = MutableLiveData<UserResponse>()
    val failedGetUserResponse = MutableLiveData<String?>()

    /**
     * create Health as Json
     */
    fun createHealth(health: HealthResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.createHealth(health)
                if (response.isSuccessful) {
                    successCreateHealthResponse.postValue(response.body())
                    Log.e(TAG, "create successful: ${response.body()}")
                } else {
                    failedCreateHealthResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedCreateHealthResponse.postValue("failed.")
            }
        }
    }

    /**
     * Get Healths By id
     */

    fun getHealthById(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getHealthById(userId = userId)
                if (response.isSuccessful) {
                    successGetHealthResponse.postValue(response.body())
                    Log.e(TAG, "Healths: ${response.body()}")
                } else {
                    failedGetHealthResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedGetHealthResponse.postValue("failed.")
            }
        }
    }

    /**
     * update Health by id
     */
    fun updateHealth(health: HealthResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.updateHealth(health = health)
                if (response.isSuccessful) {
                    successUpdateHealthResponse.postValue(response.body())
                    Log.e(TAG, "update successful: ${response.body()}")
                } else {
                    failedUpdateHealthResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedUpdateHealthResponse.postValue("failed.")
            }
        }
    }

    /**
     * Delete Health by id
     */
    fun deleteHealth(health: HealthResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.deleteHealth(health= health)
                if (response.isSuccessful) {
                    successDeleteHealthResponse.postValue(response.body())
                    Log.e(TAG, "delete successful: ${response.body()}")
                } else {
                    failedDeleteHealthResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedDeleteHealthResponse.postValue("failed.")
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
        const val TAG = "HealthViewModel"
    }
}