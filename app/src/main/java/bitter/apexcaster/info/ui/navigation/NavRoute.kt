package bitter.apexcaster.info.ui.navigation

import kotlinx.serialization.Serializable

sealed class NavRoute {

    @Serializable
    object Splash : NavRoute()

    @Serializable
    object Onboarding : NavRoute()

    @Serializable
    object Home : NavRoute()

    @Serializable
    object Booking : NavRoute()

    @Serializable
    object History : NavRoute()

    @Serializable
    object Statistics : NavRoute()

    @Serializable
    object Settings : NavRoute()

    @Serializable
    data class ArticleDetail(val articleId: Long) : NavRoute()

    @Serializable
    data class WorkoutSession(val workoutId: Long, val type: String, val duration: Int) : NavRoute()
}
