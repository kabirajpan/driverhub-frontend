package com.driverhub.shared.domain.usecase.owner.cars

import com.driverhub.shared.domain.model.Car
import com.driverhub.shared.domain.model.CarStatus
import com.driverhub.shared.domain.repository.CarRepository

/**
 * Use case for getting all cars in the fleet
 * Handles business logic and filtering
 */
class GetAllCarsUseCase(
    private val carRepository: CarRepository
) {
    /**
     * Get all cars in the fleet
     * @param filterByStatus Optional status filter
     * @return Result with list of cars or error
     */
    suspend operator fun invoke(filterByStatus: CarStatus? = null): Result<List<Car>> {
        return try {
            val result = if (filterByStatus != null) {
                carRepository.getCarsByStatus(filterByStatus)
            } else {
                carRepository.getAllCars()
            }
            
            if (result.isSuccess) {
                val cars = result.getOrNull() ?: emptyList()
                // Sort: Active first, then by name
                val sortedCars = cars.sortedWith(
                    compareByDescending<Car> { it.status == CarStatus.ACTIVE }
                        .thenBy { it.name }
                )
                Result.success(sortedCars)
            } else {
                result
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get count of cars by status
     * @return Map of status to count
     */
    suspend fun getCarCountsByStatus(): Result<Map<CarStatus, Int>> {
        return try {
            val result = carRepository.getAllCars()
            
            if (result.isSuccess) {
                val cars = result.getOrNull() ?: emptyList()
                val counts = cars.groupingBy { it.status }.eachCount()
                Result.success(counts)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
