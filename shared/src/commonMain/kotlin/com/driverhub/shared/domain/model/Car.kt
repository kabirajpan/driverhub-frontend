package com.driverhub.shared.domain.model

/**
 * Domain model representing a Car in the fleet
 * This is shared between Android and iOS
 */
data class Car(
    val id: String,
    val name: String,
    val licensePlate: String,
    val model: String,
    val year: Int,
    val color: String,
    val status: CarStatus,
    val currentDriver: Driver? = null,
    val currentSpeed: Double = 0.0, // in km/h
    val location: Location? = null,
    val lastUpdated: Long = System.currentTimeMillis()
)

/**
 * Car status enum
 */
enum class CarStatus {
    ACTIVE,      // Currently driving
    IDLE,        // Parked/stopped
    MAINTENANCE, // Under maintenance
    INACTIVE     // Not in use
}

/**
 * Location data for car tracking
 */
data class Location(
    val latitude: Double,
    val longitude: Double,
    val address: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
