package bitter.apexcaster.info.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import bitter.apexcaster.info.data.local.OCEBVDatabase
import bitter.apexcaster.info.data.repository.OCEBVArticleRepository
import bitter.apexcaster.info.data.repository.UserPreferencesRepository
import bitter.apexcaster.info.data.repository.OCEBVWorkoutRepository

/**
 * Everything in the data layer: the database, its DAOs and the repositories built on top.
 * All singletons — they hold no per-screen state and are shared across the whole app.
 *
 * Register new storage, DAOs and repositories here; ViewModels belong in [viewModule].
 */
val dataModule = module {
    single {
        OCEBVDatabase.getDatabase(
            androidContext(),
            CoroutineScope(SupervisorJob() + Dispatchers.IO)
        )
    }
    single { get<OCEBVDatabase>().workoutDao() }
    single { get<OCEBVDatabase>().articleDao() }

    single { OCEBVWorkoutRepository(get()) }
    single { OCEBVArticleRepository(get()) }
    single { UserPreferencesRepository(androidContext()) }
}
