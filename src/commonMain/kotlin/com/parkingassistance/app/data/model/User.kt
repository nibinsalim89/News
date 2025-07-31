package com.parkingassistance.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String,
    val role: UserRole,
    val fullName: String,
    val phoneNumber: String?,
    val isActive: Boolean = true,
    val createdAt: Long,
    val lastLoginAt: Long?
)

@Serializable
enum class UserRole {
    ADMIN,
    USER
}