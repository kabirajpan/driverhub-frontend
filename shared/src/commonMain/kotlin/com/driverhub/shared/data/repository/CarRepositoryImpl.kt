package com.driverhub.shared.data.repository

import com.driverhub.shared.domain.model.*
import com.driverhub.shared.domain.repository.CarRepository
import kotlinx.coroutines.delay

/**
 * Mock implementation of CarRepository
 * Uses in-memory data for testing and development
 * Replace with real API implementation later
 */
class CarRepositoryImpl : CarRepository {
    
    // Mock drivers
    private val mockDrivers = listOf(
        Driver(
            id = "d1",
            name = "Rajesh Kumar",
            phone = "+91 98765 43210",
            email = "rajesh@example.com",
            licenseNumber = "MP09-2019-0001234",
            licenseExpiry = System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000),
            rating = 4.5,
            totalTrips = 245,
            status = DriverStatus.ON_TRIP,
            assignedCarId = "c1"
        ),
        Driver(
            id = "d2",
            name = "Amit Sharma",
            phone = "+91 98765 43211",
            licenseNumber = "MP09-2020-0001235",
            licenseExpiry = System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000),
            rating = 4.8,
            totalTrips = 312,
            status = DriverStatus.ON_TRIP,
            assignedCarId = "c2"
        ),
        Driver(
            id = "d3",
            name = "Priya Patel",
            phone = "+91 98765 43212",
            licenseNumber = "MP09-2021-0001236",
            licenseExpiry = System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000),
            rating = 4.3,
            totalTrips = 189,
            status = DriverStatus.AVAILABLE,
            assignedCarId = "c3"
        ),
        Driver(
            id = "d4",
            name = "Vikram Singh",
            phone = "+91 98765 43213",
            licenseNumber = "MP09-2019-0001237",
            licenseExpiry = System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000),
            rating = 4.6,
            totalTrips = 278,
            status = DriverStatus.ON_TRIP,
            assignedCarId = "c4"
        ),
        Driver(
            id = "d5",
            name = "Neha Gupta",
            phone = "+91 98765 43214",
            licenseNumber = "MP09-2020-0001238",
            licenseExpiry = System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000),
            rating = 4.7,
            totalTrips = 296,
            status = DriverStatus.ON_TRIP,
            assignedCarId = "c5"
        ),
        Driver(
            id = "d6",
            name = "Rahul Verma",
            phone = "+91 98765 43215",
            licenseNumber = "MP09-2021-0001239",
            licenseExpiry = System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000),
            rating = 4.2,
            totalTrips = 156,
            status = DriverStatus.AVAILABLE,
            assignedCarId = "c6"
        )
    )
    
    // Mock cars data
    private val mockCars = mutableListOf(
        Car(
            id = "c1",
            name = "Toyota Camry",
            licensePlate = "MP 09 AB 1234",
            model = "Camry 2022",
            year = 2022,
            color = "White",
            status = CarStatus.ACTIVE,
            currentDriver = mockDrivers[0],
            currentSpeed = 45.0,
            location = Location(23.2599, 77.4126, "Ring Road, Bhopal")
        ),
        Car(
            id = "c2",
            name = "Honda City",
            licensePlate = "MP 09 CD 5678",
            model = "City ZX",
            year = 2023,
            color = "Silver",
            status = CarStatus.ACTIVE,
            currentDriver = mockDrivers[1],
            currentSpeed = 60.0,
            location = Location(23.2470, 77.4193, "DB Mall, Bhopal")
        ),
        Car(
            id = "c3",
            name = "Maruti Swift",
            licensePlate = "MP 09 EF 9012",
            model = "Swift VXI",
            year = 2021,
            color = "Red",
            status = CarStatus.IDLE,
            currentDriver = mockDrivers[2],
            currentSpeed = 0.0,
            location = Location(23.2315, 77.4219, "Railway Station, Bhopal")
        ),
        Car(
            id = "c4",
            name = "Hyundai Creta",
            licensePlate = "MP 09 GH 3456",
            model = "Creta SX",
            year = 2023,
            color = "Blue",
            status = CarStatus.ACTIVE,
            currentDriver = mockDrivers[3],
            currentSpeed = 55.0,
            location = Location(23.2876, 77.3376, "Airport Road, Bhopal")
        ),
        Car(
            id = "c5",
            name = "Tata Nexon",
            licensePlate = "MP 09 IJ 7890",
            model = "Nexon XZ+",
            year = 2022,
            color = "Black",
            status = CarStatus.ACTIVE,
            currentDriver = mockDrivers[4],
            currentSpeed = 40.0,
            location = Location(23.2156, 77.4304, "New Market, Bhopal")
        ),
        Car(
            id = "c6",
            name = "Mahindra XUV",
            licensePlate = "MP 09 KL 2345",
            model = "XUV700",
            year = 2023,
            color = "Grey",
            status = CarStatus.IDLE,
            currentDriver = mockDrivers[5],
            currentSpeed = 0.0,
            location = Location(23.2420, 77.4066, "ISBT, Bhopal")
        )
    )
    
    override suspend fun getAllCars(): Result<List<Car>> {
        return try {
            delay(500) // Simulate network delay
            Result.success(mockCars.toList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getCarsByStatus(status: CarStatus): Result<List<Car>> {
        return try {
            delay(300)
            val filteredCars = mockCars.filter { it.status == status }
            Result.success(filteredCars)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getActiveCars(): Result<List<Car>> {
        return try {
            delay(300)
            val activeCars = mockCars.filter { it.status == CarStatus.ACTIVE }
            Result.success(activeCars)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getCarById(carId: String): Result<Car?> {
        return try {
            delay(200)
            val car = mockCars.find { it.id == carId }
            Result.success(car)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getCarsByDriver(driverId: String): Result<List<Car>> {
        return try {
            delay(300)
            val driverCars = mockCars.filter { it.currentDriver?.id == driverId }
            Result.success(driverCars)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateCarLocation(
        carId: String,
        latitude: Double,
        longitude: Double,
        speed: Double
    ): Result<Unit> {
        return try {
            delay(100)
            val carIndex = mockCars.indexOfFirst { it.id == carId }
            if (carIndex != -1) {
                val car = mockCars[carIndex]
                mockCars[carIndex] = car.copy(
                    currentSpeed = speed,
                    location = Location(latitude, longitude),
                    lastUpdated = System.currentTimeMillis()
                )
                Result.success(Unit)
            } else {
                Result.failure(Exception("Car not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateCarStatus(carId: String, status: CarStatus): Result<Unit> {
        return try {
            delay(100)
            val carIndex = mockCars.indexOfFirst { it.id == carId }
            if (carIndex != -1) {
                val car = mockCars[carIndex]
                mockCars[carIndex] = car.copy(
                    status = status,
                    lastUpdated = System.currentTimeMillis()
                )
                Result.success(Unit)
            } else {
                Result.failure(Exception("Car not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
