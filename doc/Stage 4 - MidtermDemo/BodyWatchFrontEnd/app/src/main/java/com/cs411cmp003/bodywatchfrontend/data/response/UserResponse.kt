package com.cs411cmp003.bodywatchfrontend.data.response

import java.io.Serializable

data class UserResponse(
    val email: String?,
    val firstName: String?,
    val height: Int?,
    val lastName: String?,
    val phoneNumber: String?,
    var userId: Int,
    val weight: Int?
): Serializable