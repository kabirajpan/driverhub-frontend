package com.driverhub.shared.domain.model

/**
 * Domain model representing a Driver
 * This is shared between Android and iOS
 */
data class Driver(
    val id: String,
    val name: String,
    val phone: String,
    val email: String? = null,
    val licenseNumber: String,
    val licenseExpiry: Long,
    val photo: String? = null, // URL to profile photo
    val rating: Double = 0.0, // 0.0 to 5.0
    val totalTrips: Int = 0,
    val status: DriverStatus = DriverStatus.AVAILABLE,
    val assignedCarId: String? = null,
    val joinedDate: Long = System.currentTimeMillis()
)

/**
 * Driver status enum
 */
enum class DriverStatus {
    AVAILABLE,   // Ready to drive
    ON_TRIP,     // Currently driving
    OFFLINE,     // Not available
    ON_BREAK,    // Taking a break
    SUSPENDED    // Account suspended
}
