package com.cs411cmp003.bodywatchfrontend.ui.menuUserInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs411cmp003.bodywatchfrontend.data.repository.ApiRepository
import com.cs411cmp003.bodywatchfrontend.data.response.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    var userId = MutableLiveData<String>()
    val successCreateUserResponse = MutableLiveData<String>()
    val failedCreateUserResponse = MutableLiveData<String?>()
    val successGetUserResponse = MutableLiveData<UserResponse>()
    val failedGetUserResponse = MutableLiveData<String?>()
    val successReadAllUserIdResponse = MutableLiveData<ArrayList<Int>>()
    val failedReadAllUserIdResponse = MutableLiveData<String?>()
    val successUpdateUserResponse = MutableLiveData<String>()
    val failedUpdateUserResponse = MutableLiveData<String?>()
    val successDeleteUserResponse = MutableLiveData<String>()
    val failedDeleteUserResponse = MutableLiveData<String?>()

    /**
     * create userInfo as Json
     */
    fun createUser(user: UserResponse) {
        // create your json here
        val jsonObject = JSONObject()
        try {
            jsonObject.put("userId", user.userId)
            jsonObject.put("firstName", user.firstName)
            jsonObject.put("lastName", user.lastName)
            jsonObject.put("email", user.email)
            jsonObject.put("phoneNumber", user.phoneNumber)
            jsonObject.put("weight", user.weight)
            jsonObject.put("height", user.height)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.createUser(user)
                if (response.isSuccessful) {
                    successCreateUserResponse.postValue(response.body())
                    Log.e(TAG, "create successful: ${response.body()}")
                } else {
                    failedCreateUserResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedCreateUserResponse.postValue("failed.")
            }
        }
    }

    /**
     * Get All userId
     */
    fun getAllUserId() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getAllUserId()
                if (response.isSuccessful) {
                    successReadAllUserIdResponse.postValue(response.body())
                    Log.e(TAG, "all user id:${response.body()}")
                } else {
                    failedReadAllUserIdResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedReadAllUserIdResponse.postValue("failed.")
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
                    Log.e(TAG, "user: ${response.body()}")
                } else {
                    failedGetUserResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedGetUserResponse.postValue("failed.")
            }
        }
    }

    /**
     * update User by id
     */
    fun updateUser(userId: Int, user: UserResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.updateUser(userId = userId, user = user)
                if (response.isSuccessful) {
                    successUpdateUserResponse.postValue(response.body())
                    Log.e(TAG, "update successful: ${response.body()}")
                } else {
                    failedUpdateUserResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedUpdateUserResponse.postValue("failed.")
            }
        }
    }

    /**
     * Delete User by id
     */
    fun deleteUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.deleteUser(userId = userId)
                if (response.isSuccessful) {
                    successDeleteUserResponse.postValue(response.body())
                    Log.e(TAG, "delete successful: ${response.body()}")
                } else {
                    failedDeleteUserResponse.postValue("unable to get the response, please check the network")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                failedDeleteUserResponse.postValue("failed.")
            }
        }
    }
    companion object {
        const val TAG = "UserInfoViewModel"
    }
}