package bitter.apexcaster.info.data.repository

import bitter.apexcaster.info.data.local.dao.OCEBVWorkoutDao
import bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity
import kotlinx.coroutines.flow.Flow

//[@AGENT][Single source of truth for workouts, backed by Room. Thin pass-through to WorkoutDao —
// keep it free of UI/formatting logic.
//
// A workout's lifecycle is driven entirely by the isCompleted flag:
//  - BookingScreen inserts it with isCompleted = false  -> it is a PLANNED session,
//    listed on Home so the user can start or delete it.
//  - Finishing the timer on WorkoutSessionScreen calls updateStatus(id, true) -> it becomes a
//    COMPLETED session, and only then may it appear in History and Statistics.
// Never feed allWorkouts to History/Statistics: those must filter on isCompleted = true,
// otherwise sessions the user merely booked get counted as training that actually happened.
// The database is never seeded with demo workouts — every row here is real user activity.]
class OCEBVWorkoutRepository(private val ocebvWorkoutDao: OCEBVWorkoutDao) {
    val allWorkouts: Flow<List<OCEBVWorkoutEntity>> = ocebvWorkoutDao.getAllWorkouts()

    suspend fun insert(workout: OCEBVWorkoutEntity) {
        ocebvWorkoutDao.insertWorkout(workout)
    }

    suspend fun delete(workout: OCEBVWorkoutEntity) {
        ocebvWorkoutDao.deleteWorkout(workout)
    }

    fun getWorkoutsInRange(
            start: Long,
            end: Long,
            isCompleted: Boolean
    ): Flow<List<OCEBVWorkoutEntity>> {
        return ocebvWorkoutDao.getWorkoutsInRange(start, end, isCompleted)
    }

    fun getDailyWorkouts(start: Long, end: Long): Flow<List<OCEBVWorkoutEntity>> {
        return ocebvWorkoutDao.getDailyWorkouts(start, end)
    }

    fun getWorkoutsByCompletion(isCompleted: Boolean): Flow<List<OCEBVWorkoutEntity>> {
        return ocebvWorkoutDao.getWorkoutsByCompletion(isCompleted)
    }

    suspend fun updateStatus(id: Long, isCompleted: Boolean) {
        ocebvWorkoutDao.updateWorkoutStatus(id, isCompleted)
    }
}
