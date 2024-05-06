package com.cs411cmp003.bodywatchfrontend.data.repository

import com.cs411cmp003.bodywatchfrontend.data.ApiService
import com.cs411cmp003.bodywatchfrontend.data.response.ActivityResponse
import com.cs411cmp003.bodywatchfrontend.data.response.GoalResponse
import com.cs411cmp003.bodywatchfrontend.data.response.HealthResponse
import com.cs411cmp003.bodywatchfrontend.data.response.UserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(private val apiService: ApiService) {
    /***
     * food
     */
    suspend fun getAllFood() = apiService.getAllFood()
    suspend fun getFoodByName(foodName:String) = apiService.getFoodByName(prodName = foodName)

    /**
     * User
     */
    suspend fun getAllUserId() = apiService.getAllUserId()
    suspend fun getUserById(userId: Int) = apiService.getUserById(userId = userId)
    suspend fun createUser(user: UserResponse) = apiService.createUser(user = user)
    suspend fun updateUser(user: UserResponse, userId: Int) = apiService.updateUser(user= user, userId = userId)

    suspend fun deleteUser(userId: Int) = apiService.deleteUser(userId = userId)

    /**
     * Goal
     */
    suspend fun getGoalById(userId: Int) = apiService.getGoalsById(userId = userId)
    suspend fun createGoal(goal: GoalResponse) = apiService.createGoal(goal = goal)
    suspend fun updateGoal(goal: GoalResponse, userId: Int) = apiService.updateGoal(goal= goal, userId = userId)

    suspend fun deleteGoal(userId: Int,timeline:String) = apiService.deleteGoal(userId = userId, timeline = timeline)
    /**
     * Advanced Query
     */
    suspend fun getFoodSuggestion() = apiService.getFoodSuggestion()
    suspend fun getLeadboard() = apiService.getLeaderboard()


    /**
     * Health
     */
    suspend fun getHealthById(userId: Int) = apiService.getHealthById(userId = userId)
    suspend fun createHealth(health: HealthResponse) = apiService.createHealth(health = health)
    suspend fun updateHealth(health: HealthResponse) =
        apiService.updateHealth(health= health)
    suspend fun deleteHealth(health: HealthResponse) = apiService.deleteHealth(health= health)

    /**
     * Activity
     */
    suspend fun getActivityById(userId: Int) = apiService.getActivityById(userId = userId)
    suspend fun createActivity(activity: ActivityResponse) = apiService.createActivity(activityResponse = activity)
    suspend fun updateActivity(activity: ActivityResponse, userId: Int, exercise: String, startTime:String) =
        apiService.updateActivity(activity= activity, userId = userId, exercise = exercise, startTime = startTime )
    suspend fun deleteActivity(userId: Int, exercise: String, startTime:String) =
        apiService.deleteActivity( userId = userId, exercise = exercise, startTime = startTime)

}