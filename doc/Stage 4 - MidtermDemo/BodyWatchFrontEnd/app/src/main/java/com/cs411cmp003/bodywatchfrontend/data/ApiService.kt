package com.cs411cmp003.bodywatchfrontend.data

import com.cs411cmp003.bodywatchfrontend.data.response.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /**
     * Food Api
     */
    @GET("foods")
    suspend fun getAllFood(): Response<ArrayList<FoodResponse>>

    @GET("foods/{prodName}")
    suspend fun getFoodByName(
        @Path("prodName") prodName: String//food name
    ): Response<ArrayList<FoodResponse>>


    /**
     * User Api
     */
    @GET("users/id")
    suspend fun getAllUserId(): Response<ArrayList<Int>>

    @GET("users/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: Int//user id
    ): Response<UserResponse>

    @POST("users")
    suspend fun createUser(
        @Body user: UserResponse
    ): Response<String>

    @PUT("users/{userId}")
    suspend fun updateUser(
        @Body user: UserResponse,
        @Path("userId") userId: Int//user id
    ): Response<String>

    @DELETE("users/{userId}")
    suspend fun deleteUser(
        @Path("userId") userId: Int//user id
    ): Response<String>

    /**
     * Activity Api
     */
    @GET("activities/{userId}")
    suspend fun getActivityById(
        @Path("userId") userId: Int//user id
    ): Response<ArrayList<ActivityResponse>>

    @POST("activities")
    suspend fun createActivity(
        @Body activityResponse: ActivityResponse
    ): Response<String>

    @PUT("activities/{startTime}&{userId}&{exercise}")
    suspend fun updateActivity(
        @Body activity: ActivityResponse,
        @Path("userId") userId: Int,//user id
        @Path("exercise") exercise: String,
        @Path("startTime") startTime: String
        ): Response<String>

    @DELETE("activities/{startTime}&{userId}&{exercise}")
    suspend fun deleteActivity(
        @Path("userId") userId: Int,//user id
        @Path("exercise") exercise: String,
        @Path("startTime") startTime: String
    ): Response<String>

    /**
     * Health Api
     */
    @GET("health/{userId}")
    suspend fun getHealthById(
        @Path("userId") userId: Int//user id
    ): Response<ArrayList<HealthResponse>>

    @POST("health")
    suspend fun createHealth(
        @Body health: HealthResponse
    ): Response<Int>

    @PUT("health")
    suspend fun updateHealth(
        @Body health: HealthResponse
    ): Response<String>

    @HTTP(method = "DELETE", path = "health", hasBody = true)
    suspend fun deleteHealth(
        @Body health: HealthResponse
    ): Response<String>

    /**
     * Goal Api
     */
    @GET("goals/{userId}")
    suspend fun getGoalsById(
        @Path("userId") userId: Int//user id
    ): Response<ArrayList<GoalResponse>>

    @POST("goals")
    suspend fun createGoal(
        @Body goal: GoalResponse
    ): Response<String>

    @PUT("goals/{userId}")
    suspend fun updateGoal(
        @Body goal: GoalResponse,
        @Path("userId") userId: Int//user id
    ): Response<String>

    @DELETE("goals/{userId}&{timeline}")
    suspend fun deleteGoal(
        @Path("userId") userId: Int,//user id
        @Path("timeline") timeline: String
    ): Response<String>

    /**
     * Advanced query
     */
    @GET("foods/suggestion")
    suspend fun getFoodSuggestion():Response<List<List<String>>>

    @GET("activities/leaderboard")
    suspend fun getLeaderboard(): Response<List<List<Int>>>
}