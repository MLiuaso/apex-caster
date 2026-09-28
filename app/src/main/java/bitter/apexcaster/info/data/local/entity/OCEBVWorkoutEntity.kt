package bitter.apexcaster.info.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class OCEBVWorkoutEntity(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val type: String,
        val environment: String,
        val imageUrl: String,
        val dateInMillis: Long,
        val durationMinutes: Int,
        val distanceKm: Double = 0.0,
        val caloriesBurned: Int = 0,
        val isCompleted: Boolean = true,
        val notes: String? = null
)
