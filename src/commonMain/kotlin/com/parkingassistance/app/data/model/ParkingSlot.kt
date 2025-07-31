package com.parkingassistance.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ParkingSlot(
    val id: String,
    val slotNumber: String,
    val slotType: SlotType,
    val status: SlotStatus,
    val occupiedBy: String?, // User ID
    val reservedBy: String?, // User ID
    val reservationExpiresAt: Long?, // Timestamp
    val location: SlotLocation,
    val qrCode: String?,
    val nfcTag: String?,
    val isActive: Boolean = true,
    val lastUpdated: Long,
    val metadata: Map<String, String> = emptyMap()
)

@Serializable
enum class SlotType {
    CAR,
    BIKE,
    EV_CHARGING,
    DISABLED_ACCESS,
    COMPACT,
    LARGE_VEHICLE
}

@Serializable
enum class SlotStatus {
    VACANT,
    OCCUPIED,
    RESERVED,
    OUT_OF_ORDER,
    MAINTENANCE
}

@Serializable
data class SlotLocation(
    val floor: Int?,
    val section: String?,
    val row: String?,
    val coordinates: Coordinates?
)

@Serializable
data class Coordinates(
    val x: Double,
    val y: Double,
    val latitude: Double? = null,
    val longitude: Double? = null
)