package com.cs411cmp003.bodywatchfrontend.data.response

import java.io.Serializable

data class GoalResponse(
    val caloriesGoal: Int?,
    val carbGoal: Int?,
    val fatGoal: Int?,
    val proteinGoal: Int?,
    val stepsGoal: Int?,
    val timeline: String,
    val user: UserResponse?,
    val userId: Int,
    val weightGoal: Int?
): Serializable