package com.cs411cmp003.bodywatchfrontend.data.response

import java.io.Serializable

data class FoodResponse(
    val energy100g: Double?,
    val energyFat100g: Double?,
    val foodId: Int,
    val frGrade: String?,
    val genericName: String?,
    val imageURL: String?,
    val ingredientsText: String?,
    val prodName: String?,
    val quantity: String?,
    val servSize: String?,
    val ukGrade: String?
) : Serializable