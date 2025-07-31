package com.parkingassistant.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Reservation(
    val id: String,
    val userId: String,
    val slotId: String,
    val status: ReservationStatus,
    val createdAt: Long,
    val expiresAt: Long,
    val activatedAt: Long?,
    val completedAt: Long?,
    val vehicleInfo: VehicleInfo?,
    val notes: String?
)

@Serializable
enum class ReservationStatus {
    PENDING,
    ACTIVE,
    COMPLETED,
    EXPIRED,
    CANCELLED
}

@Serializable
data class VehicleInfo(
    val licensePlate: String?,
    val vehicleType: String?,
    val color: String?,
    val model: String?
)

@Serializable
data class ParkingSession(
    val id: String,
    val userId: String,
    val slotId: String,
    val reservationId: String?,
    val startTime: Long,
    val endTime: Long?,
    val duration: Long?,
    val vehicleInfo: VehicleInfo?,
    val checkInMethod: CheckInMethod,
    val checkOutMethod: CheckInMethod?,
    val totalCost: Double?
)

@Serializable
enum class CheckInMethod {
    MANUAL,
    QR_CODE,
    NFC,
    AUTO_DETECT
}