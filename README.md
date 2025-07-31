# 🅿️ ParkingAssistant - Kotlin Multiplatform App

A comprehensive parking management application built with Kotlin Multiplatform for iOS and Android platforms.

## 🌟 Features Overview

### 👥 User Roles

#### 🔑 Admin Users
- ✅ **Slot Management**: Add, remove, or modify parking slots
- ✅ **Override Controls**: Change status of any parking slot (vacant/engaged)
- ✅ **Analytics Dashboard**: View overall parking usage statistics and user activity logs
- ✅ **Alert Management**: Handle unauthorized occupancy and system alerts
- ✅ **Real-time Monitoring**: Live view of all parking operations

#### 👤 Regular Users
- ✅ **Real-time Viewing**: See available parking slots instantly
- ✅ **Slot Reservation**: Reserve vacant parking slots with timer
- ✅ **Check-in/Check-out**: Mark slots as engaged/vacant
- ✅ **History Tracking**: View past parking sessions
- ✅ **Notifications**: Receive alerts and reminders

### 🚀 Core Features

#### 1. **⏰ Slot Reservation Timer**
- Reserve slots for limited time (configurable, default: 15 minutes)
- Automatic release if not engaged within reservation window
- Smart expiration notifications (5-minute warning)

#### 2. **📱 QR Code & NFC Based Check-in/out**
- Secure, verifiable slot engagement using QR or NFC tags
- Multiple check-in methods: Manual, QR Code, NFC, Auto-detect
- Tamper-proof verification system

#### 3. **🔔 Smart Notifications**
- **User Notifications**: Reservation expiring, slot available, payment reminders
- **Admin Alerts**: Unauthorized occupancy, system malfunctions, maintenance needs
- Push notifications for both iOS and Android

#### 4. **🗺️ Real-Time Slot Availability Map**
- Visual map showing live slot statuses with color coding
- Floor and section navigation
- Coordinate-based positioning system
- Filter by slot type and availability

#### 5. **📊 Reports & Analytics (Admin)**
- **Usage Trends**: Peak hours, daily/weekly/monthly patterns
- **Performance Metrics**: Slot utilization rates, revenue tracking
- **User Analytics**: Behavior patterns, frequent users
- **Maintenance Reports**: Equipment status, failure predictions

#### 6. **👤 User Account Management**
- Secure authentication with role-based access control
- Complete parking history with session details
- Profile management and preferences
- Vehicle information storage

#### 7. **🚗 Slot Type Classification**
- 🚗 **Standard Car Slots**
- 🏍️ **Motorcycle/Bike Slots**
- ⚡ **EV Charging Stations**
- ♿ **Disabled Access Slots**
- 🚙 **Compact Vehicle Slots**
- 🚛 **Large Vehicle Slots**

## 🏗️ Technical Architecture

### Kotlin Multiplatform Structure
```
ParkingAssistant/
├── shared/
│   ├── src/
│   │   ├── commonMain/kotlin/
│   │   │   ├── data/
│   │   │   │   ├── model/          # Core data models
│   │   │   │   ├── repository/     # Repository interfaces
│   │   │   │   └── local/          # Local storage
│   │   │   ├── domain/
│   │   │   │   ├── usecase/        # Business logic
│   │   │   │   └── repository/     # Domain contracts
│   │   │   ├── presentation/       # Shared UI logic
│   │   │   └── utils/              # Common utilities
│   │   ├── androidMain/kotlin/     # Android implementations
│   │   ├── iosMain/kotlin/         # iOS implementations
│   │   └── commonTest/kotlin/      # Shared tests
│   └── build.gradle.kts
├── androidApp/
│   ├── src/main/kotlin/           # Android UI (Compose)
│   └── build.gradle.kts
├── iosApp/
│   └── iosApp/                    # iOS UI (SwiftUI)
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts
```

### 🛠️ Technology Stack

#### Shared (commonMain)
- **Kotlin Multiplatform 1.9.21**: Cross-platform business logic
- **Kotlinx Serialization**: Type-safe JSON handling
- **Kotlinx Coroutines**: Asynchronous programming
- **Kotlinx DateTime**: Cross-platform date/time
- **SQLDelight 2.0.1**: Type-safe SQL database
- **Ktor Client 2.3.7**: HTTP networking
- **Koin 3.5.3**: Dependency injection
- **Voyager 1.0.0**: Navigation library

#### Android-specific
- **Jetpack Compose**: Modern declarative UI
- **Material Design 3**: Google's design system
- **CameraX**: QR code scanning
- **NFC API**: Near-field communication
- **WorkManager**: Background tasks

#### iOS-specific
- **SwiftUI Integration**: Native iOS UI
- **AVFoundation**: Camera and QR scanning
- **Core NFC**: NFC tag reading
- **Core Data**: Local persistence

## 📱 User Interface Design

### Android (Jetpack Compose)
- Material Design 3 components
- Adaptive layouts for tablets
- Dark/Light theme support
- Accessibility compliance

### iOS (SwiftUI)
- Native iOS design patterns
- Dynamic Type support
- VoiceOver accessibility
- Adaptive UI for iPad

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Arctic Fox or later
- **Xcode 13+**: For iOS development
- **JDK 11+**: Java development kit
- **CocoaPods**: For iOS dependencies

### Installation
```bash
# Clone the repository
git clone https://github.com/yourusername/ParkingAssistant.git
cd ParkingAssistant

# Android
./gradlew assembleDebug

# iOS (requires macOS)
cd iosApp
pod install
open iosApp.xcworkspace
```

### Development Setup
1. Open project in Android Studio
2. Sync Gradle dependencies
3. Configure local.properties for SDK paths
4. Run on emulator or physical device

## 📊 Data Models

### Core Entities
```kotlin
// User with role-based permissions
data class User(
    val id: String,
    val username: String,
    val email: String,
    val role: UserRole, // ADMIN or USER
    val fullName: String,
    val phoneNumber: String?
)

// Comprehensive parking slot
data class ParkingSlot(
    val id: String,
    val slotNumber: String,
    val slotType: SlotType,
    val status: SlotStatus,
    val location: SlotLocation,
    val qrCode: String?,
    val nfcTag: String?
)

// Reservation with timer
data class Reservation(
    val id: String,
    val userId: String,
    val slotId: String,
    val status: ReservationStatus,
    val expiresAt: Long,
    val vehicleInfo: VehicleInfo?
)
```

## 🔐 Security Features

- **Authentication**: Secure user login with JWT tokens
- **Authorization**: Role-based access control (RBAC)
- **Data Encryption**: Sensitive data encryption at rest
- **QR Security**: Cryptographically signed QR codes
- **NFC Encryption**: Secure NFC tag communication
- **Audit Logging**: Complete admin action logging

## 📈 Analytics & Reporting

### Real-time Metrics
- **Occupancy Rate**: Current utilization percentage
- **Peak Hours**: High-demand time periods
- **Slot Performance**: Individual slot usage statistics
- **Revenue Tracking**: Payment and billing analytics

### Historical Reports
- Daily/Weekly/Monthly usage patterns
- User behavior analysis
- Maintenance scheduling optimization
- Capacity planning insights

## 🔔 Notification System

### Smart Alerts
- **Reservation Expiring**: 5-minute warning before timeout
- **Slot Available**: Notify when preferred slot becomes free
- **Payment Due**: Billing and payment reminders
- **Maintenance**: Equipment status updates

### Admin Notifications
- **Unauthorized Access**: Instant security alerts
- **System Health**: Performance monitoring alerts
- **Usage Anomalies**: Unusual pattern detection

## 🎯 Roadmap

### Phase 1 (Current) ✅
- Basic slot management
- User authentication
- Real-time updates
- QR/NFC integration

### Phase 2 🚧
- Advanced analytics dashboard
- Payment integration
- Multi-language support
- Offline mode capabilities

### Phase 3 🔮
- AI-powered predictions
- IoT sensor integration
- Voice commands
- AR navigation

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

- 📧 Email: support@parkingassistant.app
- 🐛 Issues: [GitHub Issues](https://github.com/yourusername/ParkingAssistant/issues)
- 📖 Documentation: [Wiki](https://github.com/yourusername/ParkingAssistant/wiki)

---

Built with ❤️ using Kotlin Multiplatform