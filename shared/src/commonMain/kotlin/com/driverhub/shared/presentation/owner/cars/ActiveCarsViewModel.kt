package com.driverhub.shared.presentation.owner.cars

import com.driverhub.shared.domain.model.CarStatus
import com.driverhub.shared.domain.usecase.owner.cars.GetActiveCarsUseCase
import com.driverhub.shared.domain.usecase.owner.cars.GetAllCarsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for Active Cars feature
 * Manages state and business logic for active cars screen
 * Shared between Android and iOS
 */
class ActiveCarsViewModel(
    private val getActiveCarsUseCase: GetActiveCarsUseCase,
    private val getAllCarsUseCase: GetAllCarsUseCase
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main + Job())
    
    private val _uiState = MutableStateFlow(ActiveCarsUiState())
    val uiState: StateFlow<ActiveCarsUiState> = _uiState.asStateFlow()
    
    init {
        // TEMPORARY: Commented out until backend is ready
        // This was causing 401 errors on logout
        // loadCars()
        
        // TODO: Uncomment when backend cars endpoints are implemented
    }
    
    /**
     * Load all cars and calculate counts
     */
    fun loadCars() {
        // TEMPORARY: Skip API call until backend is ready
        // When backend is ready, uncomment the code below
        
        /*
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                val result = getAllCarsUseCase.invoke()
                
                if (result.isSuccess) {
                    val cars = result.getOrNull() ?: emptyList()
                    val activeCount = cars.count { it.status == CarStatus.ACTIVE }
                    val idleCount = cars.count { it.status == CarStatus.IDLE }
                    
                    _uiState.update {
                        it.copy(
                            cars = cars,
                            filteredCars = cars,
                            isLoading = false,
                            activeCount = activeCount,
                            idleCount = idleCount,
                            totalCount = cars.size,
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.exceptionOrNull()?.message ?: "Failed to load cars"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
        */
    }
    
    /**
     * Refresh cars data (pull-to-refresh)
     */
    fun refreshCars() {
        // TEMPORARY: Skip until backend is ready
        /*
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true, error = null) }
            
            try {
                val result = getAllCarsUseCase.invoke()
                
                if (result.isSuccess) {
                    val cars = result.getOrNull() ?: emptyList()
                    val activeCount = cars.count { it.status == CarStatus.ACTIVE }
                    val idleCount = cars.count { it.status == CarStatus.IDLE }
                    
                    _uiState.update {
                        it.copy(
                            cars = cars,
                            filteredCars = applyFilter(cars, it.selectedFilter),
                            isRefreshing = false,
                            activeCount = activeCount,
                            idleCount = idleCount,
                            totalCount = cars.size,
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            error = result.exceptionOrNull()?.message ?: "Failed to refresh cars"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isRefreshing = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
        */
    }
    
    /**
     * Filter cars by status
     * @param status Status to filter by, or null to show all
     */
    fun filterByStatus(status: CarStatus?) {
        _uiState.update {
            it.copy(
                selectedFilter = status,
                filteredCars = applyFilter(it.cars, status)
            )
        }
    }
    
    /**
     * Clear any error message
     */
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    
    /**
     * Helper function to apply filter
     */
    private fun applyFilter(cars: List<com.driverhub.shared.domain.model.Car>, status: CarStatus?) =
        if (status != null) {
            cars.filter { it.status == status }
        } else {
            cars
        }
    
    /**
     * Clean up coroutines when ViewModel is destroyed
     */
    fun onCleared() {
        viewModelScope.coroutineContext[Job]?.cancel()
    }
}
