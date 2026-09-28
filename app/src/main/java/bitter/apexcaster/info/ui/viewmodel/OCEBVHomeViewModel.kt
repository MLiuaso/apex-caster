package bitter.apexcaster.info.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import bitter.apexcaster.info.data.local.entity.OCEBVArticleEntity
import bitter.apexcaster.info.data.repository.OCEBVArticleRepository
import bitter.apexcaster.info.data.repository.OCEBVWorkoutRepository

class OCEBVHomeViewModel(
    private val articleRepository: OCEBVArticleRepository,
    private val workoutRepository: OCEBVWorkoutRepository
) : ViewModel() {

    val articles: StateFlow<List<OCEBVArticleEntity>> =
        articleRepository.allArticles.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val plannedWorkouts: StateFlow<List<bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity>> =
        workoutRepository
            .getWorkoutsByCompletion(isCompleted = false)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteWorkout(workout: bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity) {
        viewModelScope.launch { workoutRepository.delete(workout) }
    }
}
