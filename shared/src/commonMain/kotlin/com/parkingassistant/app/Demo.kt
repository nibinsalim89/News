package com.parkingassistant.app

import com.parkingassistant.app.data.model.*
import kotlinx.datetime.Clock

/**
 * Demo class to showcase the ParkingAssistant app functionality
 */
class ParkingAssistantDemo {
    
    fun demonstrateFeatures(): String {
        val features = buildString {
            appendLine("🅿️ ParkingAssistant - Kotlin Multiplatform Demo")
            appendLine("=" * 50)
            appendLine()
            
            // User roles demo
            appendLine("👥 User Roles:")
            appendLine("✅ Admin: Can manage all slots, override statuses")
            appendLine("✅ User: Can reserve and occupy available slots")
            appendLine()
            
            // Sample data
            val sampleSlots = createSampleSlots()
            appendLine("📊 Sample Parking Slots:")
            sampleSlots.forEach { slot ->
                val statusIcon = when(slot.status) {
                    SlotStatus.VACANT -> "🟢"
                    SlotStatus.OCCUPIED -> "🔴"
                    SlotStatus.RESERVED -> "🟡"
                    SlotStatus.OUT_OF_ORDER -> "⚫"
                    SlotStatus.MAINTENANCE -> "🔵"
                }
                appendLine("  $statusIcon ${slot.slotNumber} (${slot.slotType.name.replace("_", " ")}) - ${slot.status}")
            }
            appendLine()
            
            // Features
            appendLine("🚀 Key Features:")
            appendLine("✅ Real-time slot status tracking")
            appendLine("✅ Role-based access control")
            appendLine("✅ Reservation system with 15-min timer")
            appendLine("✅ QR code & NFC integration")
            appendLine("✅ Multiple slot types (Car, Bike, EV, Disabled Access, etc.)")
            appendLine("✅ Admin dashboard with analytics")
            appendLine("✅ Push notifications support")
            appendLine("✅ Multiplatform architecture (iOS & Android)")
            appendLine()
            
            appendLine("📱 Available: ${sampleSlots.count { it.status == SlotStatus.VACANT }} slots")
            appendLine("🚗 Occupied: ${sampleSlots.count { it.status == SlotStatus.OCCUPIED }} slots")
            appendLine("📋 Reserved: ${sampleSlots.count { it.status == SlotStatus.RESERVED }} slots")
        }
        
        return features
    }
    
    private fun createSampleSlots(): List<ParkingSlot> {
        val now = Clock.System.now().toEpochMilliseconds()
        return listOf(
            ParkingSlot(
                id = "1", slotNumber = "A01", slotType = SlotType.CAR, status = SlotStatus.VACANT,
                occupiedBy = null, reservedBy = null, reservationExpiresAt = null,
                location = SlotLocation(1, "A", "1", Coordinates(10.0, 20.0)),
                qrCode = "QR_A01", nfcTag = "NFC_A01", lastUpdated = now
            ),
            ParkingSlot(
                id = "2", slotNumber = "A02", slotType = SlotType.DISABLED_ACCESS, status = SlotStatus.VACANT,
                occupiedBy = null, reservedBy = null, reservationExpiresAt = null,
                location = SlotLocation(1, "A", "1", Coordinates(15.0, 20.0)),
                qrCode = "QR_A02", nfcTag = "NFC_A02", lastUpdated = now
            ),
            ParkingSlot(
                id = "3", slotNumber = "B01", slotType = SlotType.EV_CHARGING, status = SlotStatus.OCCUPIED,
                occupiedBy = "user123", reservedBy = null, reservationExpiresAt = null,
                location = SlotLocation(1, "B", "1", Coordinates(30.0, 40.0)),
                qrCode = "QR_B01", nfcTag = "NFC_B01", lastUpdated = now
            ),
            ParkingSlot(
                id = "4", slotNumber = "B02", slotType = SlotType.BIKE, status = SlotStatus.RESERVED,
                occupiedBy = null, reservedBy = "user456", reservationExpiresAt = now + (15 * 60 * 1000),
                location = SlotLocation(1, "B", "1", Coordinates(35.0, 40.0)),
                qrCode = "QR_B02", nfcTag = "NFC_B02", lastUpdated = now
            ),
            ParkingSlot(
                id = "5", slotNumber = "C01", slotType = SlotType.COMPACT, status = SlotStatus.MAINTENANCE,
                occupiedBy = null, reservedBy = null, reservationExpiresAt = null,
                location = SlotLocation(2, "C", "1", Coordinates(50.0, 60.0)),
                qrCode = "QR_C01", nfcTag = "NFC_C01", lastUpdated = now
            )
        )
    }
}

private operator fun String.times(count: Int): String = this.repeat(count)