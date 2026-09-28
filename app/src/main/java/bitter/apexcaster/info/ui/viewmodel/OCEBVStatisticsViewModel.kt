package bitter.apexcaster.info.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity
import bitter.apexcaster.info.data.repository.OCEBVWorkoutRepository

class OCEBVStatisticsViewModel(
    workoutRepository: OCEBVWorkoutRepository
) : ViewModel() {

    private val workouts: StateFlow<List<OCEBVWorkoutEntity>> =
        workoutRepository.getWorkoutsByCompletion(isCompleted = true)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalWorkouts: StateFlow<Int> = workouts.map { it.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalDurationMinutes: StateFlow<Int> =
        workouts.map { list -> list.sumOf { it.durationMinutes } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val workoutsByType: StateFlow<Map<String, Int>> = workouts.map { list ->
        list.groupingBy { it.type }.eachCount()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())
}
