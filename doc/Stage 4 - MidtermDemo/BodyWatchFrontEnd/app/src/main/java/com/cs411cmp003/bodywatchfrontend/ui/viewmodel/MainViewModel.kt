package com.cs411cmp003.bodywatchfrontend.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs411cmp003.bodywatchfrontend.data.repository.ApiRepository
import com.cs411cmp003.bodywatchfrontend.data.response.FoodResponse
import com.cs411cmp003.bodywatchfrontend.util.Server
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository : ApiRepository): ViewModel() {
    var server: Server? = null

    val successFoodResponse = MutableLiveData<ArrayList<FoodResponse>>()
    val failedFoodResponse = MutableLiveData<String?>()
    val successSearchResponse = MutableLiveData<ArrayList<FoodResponse>>()
    val failedSearchResponse = MutableLiveData<String?>()
    val successSuggestionResponse = MutableLiveData<List<List<String>>>()
    val failedSuggestionResponse = MutableLiveData<String?>()
    val successLeadboardResponse = MutableLiveData<List<List<Int>>>()
    val failedLeadboardResponse = MutableLiveData<String?>()

    //get api response
    fun getAllFood(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = repository.getAllFood()
                if (response.isSuccessful){
                    successFoodResponse.postValue(response.body())
                    Log.e(TAG, "getAllFood: item:${response.body()}", )
                }
                else{
                    failedFoodResponse.postValue("unable to get the response, please check the network")
                }
            }
            catch (e:Exception){
                e.printStackTrace()
              //  failedFoodResponse.postValue("failed.")
            }
        }
    }

    fun getFoodByName(foodName:String){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = repository.getFoodByName(foodName)
                if (response.isSuccessful){
                    successSearchResponse.postValue(response.body())
                    Log.e(TAG, "search Food: item:${response.body()}", )
                }
                else{
                    failedSearchResponse.postValue("unable to get the response, please check the network")
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                failedSearchResponse.postValue("failed.")
            }
        }
    }
    /**
     * Get advanced query
     */
    fun getFoodSuggestion(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = repository.getFoodSuggestion()
                if (response.isSuccessful){
                    successSuggestionResponse.postValue(response.body())
                    Log.e(TAG, "search Food: item:${response.body()}", )
                }
                else{
                    failedSuggestionResponse.postValue("unable to get the response, please check the network")
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                failedSearchResponse.postValue("failed.")
            }
        }
    }

    /**
     * get leadboard
     */
    fun getLeadboard(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                val response = repository.getLeadboard()
                if (response.isSuccessful){
                    successLeadboardResponse.postValue(response.body())
                    Log.e(TAG, "search Food: item:${response.body()}", )
                }
                else{
                    failedLeadboardResponse.postValue("unable to get the response, please check the network")
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                failedSearchResponse.postValue("failed.")
            }
        }
    }
    fun clearData(){
        //successFoodResponse.postValue(null)
        failedFoodResponse.postValue(null)
        failedSearchResponse.postValue(null)
    }
    fun startService(port: Int) {
        server?.startServer(port)
    }
    companion object{
        private const val TAG ="MainViewModel"
    }

}