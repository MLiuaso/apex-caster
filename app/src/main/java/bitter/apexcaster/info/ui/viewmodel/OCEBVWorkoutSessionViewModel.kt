package bitter.apexcaster.info.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import bitter.apexcaster.info.data.repository.OCEBVWorkoutRepository

class OCEBVWorkoutSessionViewModel(private val workoutRepository: OCEBVWorkoutRepository) :
    ViewModel() {

    fun finishWorkout(workoutId: Long) {
        viewModelScope.launch { workoutRepository.updateStatus(workoutId, isCompleted = true) }
    }
}
