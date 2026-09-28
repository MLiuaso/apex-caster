package bitter.apexcaster.info.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import bitter.apexcaster.info.data.repository.UserPreferencesRepository

class OCEBVOCEBVOnboardingVM(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun completeOnboarding() {
        viewModelScope.launch {
            userPreferencesRepository.setOnboardingCompleted(true)
        }
    }
}
