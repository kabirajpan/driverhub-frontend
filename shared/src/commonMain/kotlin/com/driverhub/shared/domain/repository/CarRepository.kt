package com.driverhub.shared.domain.repository

import com.driverhub.shared.domain.model.Car
import com.driverhub.shared.domain.model.CarStatus

/**
 * Repository interface for Car operations
 * Defines the contract for car data access
 * Implementation can be mock data, API calls, or local database
 */
interface CarRepository {
    
    /**
     * Get all cars in the fleet
     * @return List of all cars
     */
    suspend fun getAllCars(): Result<List<Car>>
    
    /**
     * Get cars by status
     * @param status Filter by car status
     * @return List of cars matching the status
     */
    suspend fun getCarsByStatus(status: CarStatus): Result<List<Car>>
    
    /**
     * Get active cars (currently being driven)
     * @return List of active cars
     */
    suspend fun getActiveCars(): Result<List<Car>>
    
    /**
     * Get a specific car by ID
     * @param carId The car identifier
     * @return Car details or null if not found
     */
    suspend fun getCarById(carId: String): Result<Car?>
    
    /**
     * Get cars assigned to a specific driver
     * @param driverId The driver identifier
     * @return List of cars assigned to the driver
     */
    suspend fun getCarsByDriver(driverId: String): Result<List<Car>>
    
    /**
     * Update car location and speed
     * @param carId The car identifier
     * @param latitude Location latitude
     * @param longitude Location longitude
     * @param speed Current speed in km/h
     * @return Success or failure
     */
    suspend fun updateCarLocation(
        carId: String,
        latitude: Double,
        longitude: Double,
        speed: Double
    ): Result<Unit>
    
    /**
     * Update car status
     * @param carId The car identifier
     * @param status New car status
     * @return Success or failure
     */
    suspend fun updateCarStatus(carId: String, status: CarStatus): Result<Unit>
}
