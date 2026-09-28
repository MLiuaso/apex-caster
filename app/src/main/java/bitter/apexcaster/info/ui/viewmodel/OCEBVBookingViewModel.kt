package bitter.apexcaster.info.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity
import bitter.apexcaster.info.data.repository.OCEBVWorkoutRepository

class OCEBVBookingViewModel(private val workoutRepository: OCEBVWorkoutRepository) : ViewModel() {
    private val _trainingType = MutableStateFlow("")
    val trainingType = _trainingType.asStateFlow()

    private val _environment = MutableStateFlow("")
    val environment = _environment.asStateFlow()

    private val _notes = MutableStateFlow("")
    val notes = _notes.asStateFlow()

    private val _selectedDate = MutableStateFlow(System.currentTimeMillis())
    val selectedDate = _selectedDate.asStateFlow()

    private val _durationMinutes = MutableStateFlow("60")
    val durationMinutes = _durationMinutes.asStateFlow()

    private val _bookingEvent = MutableSharedFlow<Unit>()
    val bookingEvent = _bookingEvent.asSharedFlow()

    fun onTrainingTypeChange(newValue: String) {
        _trainingType.value = newValue
    }

    fun onEnvironmentChange(newValue: String) {
        _environment.value = newValue
    }

    fun onNotesChange(newValue: String) {
        _notes.value = newValue
    }

    fun onDateChange(newDate: Long) {
        _selectedDate.value = newDate
    }

    fun onDurationChange(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            _durationMinutes.value = newValue
        }
    }

    fun submitBooking() {
        val type = _trainingType.value
        val env = _environment.value
        if (type.isBlank() || env.isBlank()) return

        val imageUrl =
            when (type) {
                "Running" ->
                    "https://www.runsociety.com/wp-content/uploads/2019/10/run-alone-3.jpg"

                "Swimming" ->
                    "https://images.unsplash.com/photo-1530549387789-4c1017266635?auto=format&fit=crop&w=800&q=80"

                "Cycling" ->
                    "https://images.unsplash.com/photo-1541625602330-2277a4c46182?auto=format&fit=crop&w=800&q=80"

                "Gym" ->
                    "https://images.unsplash.com/photo-1517836357463-d25dfeac3438?auto=format&fit=crop&w=800&q=80"

                "Yoga" ->
                    "https://images.unsplash.com/photo-1544367563-12123d8965cd?auto=format&fit=crop&w=800&q=80"

                "HIIT" ->
                    "https://images.unsplash.com/photo-1601422407692-ec4eeec1d9b3?auto=format&fit=crop&w=800&q=80"

                else ->
                    "https://images.unsplash.com/photo-1517960413843-0aee8e2b3285?auto=format&fit=crop&w=800&q=80"
            }

        viewModelScope.launch {
            val duration = _durationMinutes.value.toIntOrNull() ?: 60
            val (calories, distance) = calculateStats(type, duration)

            val workout =
                OCEBVWorkoutEntity(
                    type = type,
                    environment = env,
                    imageUrl = imageUrl,
                    dateInMillis = _selectedDate.value,
                    durationMinutes = duration,
                    distanceKm = distance,
                    caloriesBurned = calories,
                    isCompleted = false,
                    notes = _notes.value
                )
            workoutRepository.insert(workout)
            _bookingEvent.emit(Unit)
        }
    }

    private fun calculateStats(type: String, durationMin: Int): Pair<Int, Double> {
        return when (type) {
            "Running" -> {
                val dist = (durationMin / 60.0) * 10.0
                val cal = durationMin * 11
                Pair(cal, dist)
            }

            "Swimming" -> {
                val dist = (durationMin / 60.0) * 3.0
                val cal = durationMin * 9
                Pair(cal, dist)
            }

            "Cycling" -> {
                val dist = (durationMin / 60.0) * 20.0
                val cal = durationMin * 8
                Pair(cal, dist)
            }

            "Gym" -> {
                val cal = durationMin * 6
                Pair(cal, 0.0)
            }

            "Yoga" -> {
                val cal = durationMin * 3
                Pair(cal, 0.0)
            }

            "HIIT" -> {
                val cal = durationMin * 12
                Pair(cal, 0.0)
            }

            else -> {
                val cal = durationMin * 5
                Pair(cal, 0.0)
            }
        }
    }
}
