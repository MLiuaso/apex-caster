package bitter.apexcaster.info.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity
import bitter.apexcaster.info.data.repository.OCEBVWorkoutRepository

class OCEBVHistoryViewModel(private val workoutRepository: OCEBVWorkoutRepository) : ViewModel() {

    val workouts: StateFlow<List<OCEBVWorkoutEntity>> =
        workoutRepository
            .getWorkoutsByCompletion(isCompleted = true)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteWorkout(workout: OCEBVWorkoutEntity) {
        viewModelScope.launch { workoutRepository.delete(workout) }
    }
}
