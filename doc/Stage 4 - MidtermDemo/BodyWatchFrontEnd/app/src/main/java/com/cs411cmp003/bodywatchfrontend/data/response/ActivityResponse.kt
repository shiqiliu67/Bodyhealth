package com.cs411cmp003.bodywatchfrontend.data.response

import java.io.Serializable

data class ActivityResponse (
    val avgHeartRate: Int?,
    val caloriesBurned: Int?,
    val date: String,
    val endTime: String?,
    val exercise: String,
    val startTime: String,
    val steps: Int?,
    val user: UserResponse?,
    val userId: Int?
): Serializable