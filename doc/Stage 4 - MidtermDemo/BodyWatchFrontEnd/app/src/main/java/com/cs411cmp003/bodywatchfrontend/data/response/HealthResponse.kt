package com.cs411cmp003.bodywatchfrontend.data.response

import java.io.Serializable

data class HealthResponse(
    val avgHeartRate: Int?,
    val calories_burned: Int?,
    val date: String,
    val steps: Int?,
    val user: UserResponse?,
    val userId: Int
): Serializable