package com.driverhub.shared.presentation.owner.cars

import com.driverhub.shared.domain.model.Car
import com.driverhub.shared.domain.model.CarStatus

/**
 * UI State for Active Cars screen
 * Represents all possible states the UI can be in
 */
data class ActiveCarsUiState(
    val cars: List<Car> = emptyList(),
    val filteredCars: List<Car> = emptyList(),
    val selectedFilter: CarStatus? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val activeCount: Int = 0,
    val idleCount: Int = 0,
    val totalCount: Int = 0
) {
    /**
     * Helper to check if we have data to display
     */
    val hasData: Boolean
        get() = cars.isNotEmpty()
    
    /**
     * Helper to check if we should show empty state
     */
    val showEmptyState: Boolean
        get() = !isLoading && !hasData && error == null
    
    /**
     * Helper to get the display list based on filter
     */
    val displayList: List<Car>
        get() = if (selectedFilter != null) filteredCars else cars
}
