package com.parkingassistant.app.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.parkingassistant.app.data.model.*
import kotlinx.datetime.Clock

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkingAssistantTheme {
                ParkingAssistantApp()
            }
        }
    }
}

@Composable
fun ParkingAssistantTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF1976D2),
            secondary = Color(0xFF388E3C),
            tertiary = Color(0xFFFF5722)
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingAssistantApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var currentUser by remember { mutableStateOf(createSampleUser()) }
    val parkingSlots = remember { createSampleParkingSlots() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "🅿️ Parking Assistant",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                actions = {
                    IconButton(onClick = { /* Profile action */ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                    IconButton(onClick = { /* Settings action */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                listOf(
                    "Slots" to Icons.Default.LocalParking,
                    "Map" to Icons.Default.Map,
                    "QR Scan" to Icons.Default.QrCodeScanner,
                    if (currentUser.role == UserRole.ADMIN) "Admin" to Icons.Default.AdminPanelSettings 
                    else "History" to Icons.Default.History
                ).forEachIndexed { index, (label, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> ParkingSlotsScreen(parkingSlots, currentUser)
                1 -> ParkingMapScreen(parkingSlots)
                2 -> QRScannerScreen()
                3 -> if (currentUser.role == UserRole.ADMIN) {
                    AdminDashboardScreen(parkingSlots)
                } else {
                    ParkingHistoryScreen(currentUser.id)
                }
            }
        }
    }
}

@Composable
fun ParkingSlotsScreen(slots: List<ParkingSlot>, currentUser: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Filter chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            items(SlotType.values()) { type ->
                FilterChip(
                    onClick = { /* Filter by type */ },
                    label = { Text(type.name.replace("_", " ")) },
                    selected = false
                )
            }
        }
        
        // Available slots
        Text(
            text = "Available Slots (${slots.count { it.status == SlotStatus.VACANT }})",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(slots) { slot ->
                SlotCard(
                    slot = slot,
                    onSlotAction = { action ->
                        // Handle slot actions (reserve, occupy, etc.)
                    },
                    currentUser = currentUser
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlotCard(
    slot: ParkingSlot,
    onSlotAction: (String) -> Unit,
    currentUser: User
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (slot.status) {
                SlotStatus.VACANT -> Color(0xFFE8F5E8)
                SlotStatus.OCCUPIED -> Color(0xFFFFEBEE)
                SlotStatus.RESERVED -> Color(0xFFFFF3E0)
                SlotStatus.OUT_OF_ORDER -> Color(0xFFE0E0E0)
                SlotStatus.MAINTENANCE -> Color(0xFFE1F5FE)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Slot ${slot.slotNumber}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${slot.slotType.name.replace("_", " ")} • Floor ${slot.location.floor}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                StatusChip(status = slot.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (slot.status) {
                    SlotStatus.VACANT -> {
                        Button(
                            onClick = { onSlotAction("reserve") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.BookmarkAdd, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reserve")
                        }
                        
                        OutlinedButton(
                            onClick = { onSlotAction("occupy") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.DirectionsCar, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Occupy")
                        }
                    }
                    
                    SlotStatus.OCCUPIED -> {
                        if (slot.occupiedBy == currentUser.id || currentUser.role == UserRole.ADMIN) {
                            Button(
                                onClick = { onSlotAction("vacate") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Icon(Icons.Default.ExitToApp, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Vacate")
                            }
                        }
                    }
                    
                    SlotStatus.RESERVED -> {
                        if (slot.reservedBy == currentUser.id) {
                            Button(
                                onClick = { onSlotAction("activate") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Activate")
                            }
                        }
                    }
                    
                    else -> { /* No actions for out of order or maintenance */ }
                }
                
                // QR/NFC scan button
                if (slot.status == SlotStatus.VACANT || 
                    (slot.status == SlotStatus.RESERVED && slot.reservedBy == currentUser.id)) {
                    IconButton(
                        onClick = { onSlotAction("scan") }
                    ) {
                        Icon(Icons.Default.QrCode2, contentDescription = "QR/NFC Scan")
                    }
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: SlotStatus) {
    val (color, text) = when (status) {
        SlotStatus.VACANT -> Color(0xFF4CAF50) to "Available"
        SlotStatus.OCCUPIED -> Color(0xFFF44336) to "Occupied"
        SlotStatus.RESERVED -> Color(0xFFFF9800) to "Reserved"
        SlotStatus.OUT_OF_ORDER -> Color(0xFF9E9E9E) to "Out of Order"
        SlotStatus.MAINTENANCE -> Color(0xFF2196F3) to "Maintenance"
    }
    
    Surface(
        color = color,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ParkingMapScreen(slots: List<ParkingSlot>) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Map,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Interactive Parking Map",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Visual representation of all parking slots with real-time status",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun QRScannerScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.QrCodeScanner,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "QR Code Scanner",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Scan QR codes on parking slots for quick check-in/out",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /* Start camera */ }
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start Camera")
            }
        }
    }
}

@Composable
fun AdminDashboardScreen(slots: List<ParkingSlot>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Admin Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            // Statistics cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Total Slots",
                    value = slots.size.toString(),
                    icon = Icons.Default.LocalParking,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Occupied",
                    value = slots.count { it.status == SlotStatus.OCCUPIED }.toString(),
                    icon = Icons.Default.DirectionsCar,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Available",
                    value = slots.count { it.status == SlotStatus.VACANT }.toString(),
                    icon = Icons.Default.CheckCircle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Quick Actions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Add slot */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Add Slot")
                        }
                        
                        OutlinedButton(
                            onClick = { /* View alerts */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Alerts")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ParkingHistoryScreen(userId: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.History,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Parking History",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "View your past parking sessions and reservations",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Sample data functions
fun createSampleUser(): User {
    return User(
        id = "user_1",
        username = "johndoe",
        email = "john.doe@example.com",
        role = UserRole.USER, // Change to UserRole.ADMIN to see admin features
        fullName = "John Doe",
        phoneNumber = "+1234567890",
        isActive = true,
        createdAt = Clock.System.now().toEpochMilliseconds(),
        lastLoginAt = Clock.System.now().toEpochMilliseconds()
    )
}

fun createSampleParkingSlots(): List<ParkingSlot> {
    val now = Clock.System.now().toEpochMilliseconds()
    return listOf(
        ParkingSlot(
            id = "slot_1",
            slotNumber = "A01",
            slotType = SlotType.CAR,
            status = SlotStatus.VACANT,
            occupiedBy = null,
            reservedBy = null,
            reservationExpiresAt = null,
            location = SlotLocation(1, "A", "1", Coordinates(10.0, 20.0)),
            qrCode = "QR_A01",
            nfcTag = "NFC_A01",
            lastUpdated = now
        ),
        ParkingSlot(
            id = "slot_2",
            slotNumber = "A02",
            slotType = SlotType.DISABLED_ACCESS,
            status = SlotStatus.VACANT,
            occupiedBy = null,
            reservedBy = null,
            reservationExpiresAt = null,
            location = SlotLocation(1, "A", "1", Coordinates(15.0, 20.0)),
            qrCode = "QR_A02",
            nfcTag = "NFC_A02",
            lastUpdated = now
        ),
        ParkingSlot(
            id = "slot_3",
            slotNumber = "B01",
            slotType = SlotType.EV_CHARGING,
            status = SlotStatus.OCCUPIED,
            occupiedBy = "user_2",
            reservedBy = null,
            reservationExpiresAt = null,
            location = SlotLocation(1, "B", "1", Coordinates(30.0, 40.0)),
            qrCode = "QR_B01",
            nfcTag = "NFC_B01",
            lastUpdated = now
        ),
        ParkingSlot(
            id = "slot_4",
            slotNumber = "B02",
            slotType = SlotType.BIKE,
            status = SlotStatus.RESERVED,
            occupiedBy = null,
            reservedBy = "user_1",
            reservationExpiresAt = now + (15 * 60 * 1000),
            location = SlotLocation(1, "B", "1", Coordinates(35.0, 40.0)),
            qrCode = "QR_B02",
            nfcTag = "NFC_B02",
            lastUpdated = now
        ),
        ParkingSlot(
            id = "slot_5",
            slotNumber = "C01",
            slotType = SlotType.COMPACT,
            status = SlotStatus.MAINTENANCE,
            occupiedBy = null,
            reservedBy = null,
            reservationExpiresAt = null,
            location = SlotLocation(2, "C", "1", Coordinates(50.0, 60.0)),
            qrCode = "QR_C01",
            nfcTag = "NFC_C01",
            lastUpdated = now
        )
    )
}