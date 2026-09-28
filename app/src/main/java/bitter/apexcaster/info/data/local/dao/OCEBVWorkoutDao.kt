package bitter.apexcaster.info.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OCEBVWorkoutDao {
        @Query("SELECT * FROM workouts ORDER BY dateInMillis DESC")
        fun getAllWorkouts(): Flow<List<OCEBVWorkoutEntity>>

        @Query("SELECT COUNT(*) FROM workouts") suspend fun getWorkoutCount(): Int

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertWorkout(workout: OCEBVWorkoutEntity)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertWorkouts(workouts: List<OCEBVWorkoutEntity>)

        @Delete suspend fun deleteWorkout(workout: OCEBVWorkoutEntity)

        @Query(
                "SELECT * FROM workouts WHERE dateInMillis BETWEEN :startTime AND :endTime AND isCompleted = :isCompleted"
        )
        fun getWorkoutsInRange(
                startTime: Long,
                endTime: Long,
                isCompleted: Boolean
        ): Flow<List<OCEBVWorkoutEntity>>

        @Query("SELECT * FROM workouts WHERE dateInMillis BETWEEN :startTime AND :endTime")
        fun getDailyWorkouts(startTime: Long, endTime: Long): Flow<List<OCEBVWorkoutEntity>>

        @Query("SELECT * FROM workouts WHERE isCompleted = :isCompleted ORDER BY dateInMillis DESC")
        fun getWorkoutsByCompletion(isCompleted: Boolean): Flow<List<OCEBVWorkoutEntity>>

        @Query("UPDATE workouts SET isCompleted = :isCompleted WHERE id = :id")
        suspend fun updateWorkoutStatus(id: Long, isCompleted: Boolean)
}
