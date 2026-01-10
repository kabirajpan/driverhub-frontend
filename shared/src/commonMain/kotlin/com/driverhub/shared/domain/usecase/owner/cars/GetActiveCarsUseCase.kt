package com.driverhub.shared.domain.usecase.owner.cars

import com.driverhub.shared.domain.model.Car
import com.driverhub.shared.domain.repository.CarRepository

/**
 * Use case for getting all active cars in the fleet
 * Handles business logic between ViewModel and Repository
 */
class GetActiveCarsUseCase(
    private val carRepository: CarRepository
) {
    /**
     * Get all cars that are currently active (being driven)
     * @return Result with list of active cars or error
     */
    suspend operator fun invoke(): Result<List<Car>> {
        return try {
            val result = carRepository.getActiveCars()
            
            if (result.isSuccess) {
                val cars = result.getOrNull() ?: emptyList()
                // Sort by speed (fastest first)
                val sortedCars = cars.sortedByDescending { it.currentSpeed }
                Result.success(sortedCars)
            } else {
                result
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
