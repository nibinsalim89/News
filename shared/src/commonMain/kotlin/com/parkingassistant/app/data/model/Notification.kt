package com.parkingassistant.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: String,
    val userId: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val data: Map<String, String> = emptyMap(),
    val isRead: Boolean = false,
    val createdAt: Long,
    val scheduledFor: Long?,
    val priority: NotificationPriority = NotificationPriority.NORMAL
)

@Serializable
enum class NotificationType {
    RESERVATION_EXPIRING,
    RESERVATION_EXPIRED,
    SLOT_AVAILABLE,
    UNAUTHORIZED_OCCUPANCY,
    MAINTENANCE_ALERT,
    SYSTEM_ANNOUNCEMENT,
    PAYMENT_REMINDER,
    PARKING_SESSION_REMINDER
}

@Serializable
enum class NotificationPriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT
}

@Serializable
data class AdminAlert(
    val id: String,
    val type: AdminAlertType,
    val message: String,
    val slotId: String?,
    val userId: String?,
    val severity: AlertSeverity,
    val isResolved: Boolean = false,
    val createdAt: Long,
    val resolvedAt: Long?,
    val resolvedBy: String?
)

@Serializable
enum class AdminAlertType {
    UNAUTHORIZED_OCCUPANCY,
    SLOT_MALFUNCTION,
    LONG_TERM_OCCUPANCY,
    PAYMENT_ISSUE,
    SYSTEM_ERROR,
    UNUSUAL_ACTIVITY
}

@Serializable
enum class AlertSeverity {
    INFO,
    WARNING,
    ERROR,
    CRITICAL
}