package bitter.apexcaster.info.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import bitter.apexcaster.info.ui.screen.OCEBVArticleDetailScreen
import bitter.apexcaster.info.ui.screen.OCEBVBookingScreen
import bitter.apexcaster.info.ui.screen.OCEBVHistoryScreen
import bitter.apexcaster.info.ui.screen.OCEBVHomeScreen
import bitter.apexcaster.info.ui.screen.OCEBVOnboardingScreen
import bitter.apexcaster.info.ui.screen.OCEBVSettingsScreen
import bitter.apexcaster.info.ui.screen.OCEBVSplashScreen
import bitter.apexcaster.info.ui.screen.OCEBVStatisticsScreen
import bitter.apexcaster.info.ui.screen.OCEBVWorkoutSessionScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Splash,
        modifier = modifier,
    ) {
        composable<NavRoute.Splash> {
            OCEBVSplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(NavRoute.Onboarding) {
                        popUpTo(NavRoute.Splash) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(NavRoute.Home) {
                        popUpTo(NavRoute.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.Onboarding> {
            OCEBVOnboardingScreen(
                onFinish = {
                    navController.navigate(NavRoute.Home) {
                        popUpTo(NavRoute.Onboarding) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.Home> {
            OCEBVHomeScreen(
                onArticleClick = { articleId ->
                    navController.navigate(NavRoute.ArticleDetail(articleId = articleId))
                },
                onStartWorkoutClick = { workout ->
                    navController.navigate(
                        NavRoute.WorkoutSession(
                            workoutId = workout.id,
                            type = workout.type,
                            duration = workout.durationMinutes
                        )
                    )
                }
            )
        }

        composable<NavRoute.Booking> {
            OCEBVBookingScreen(
                onBookingConfirmed = {
                    navController.navigate(NavRoute.Home) {
                        popUpTo(NavRoute.Home) { inclusive = true }
                    }
                }
            )
        }

        composable<NavRoute.History> {
            OCEBVHistoryScreen()
        }

        composable<NavRoute.Statistics> {
            OCEBVStatisticsScreen()
        }

        composable<NavRoute.Settings> {
            OCEBVSettingsScreen()
        }

        composable<NavRoute.ArticleDetail> { backStackEntry ->
            val articleDetail: NavRoute.ArticleDetail = backStackEntry.toRoute()
            OCEBVArticleDetailScreen(
                articleId = articleDetail.articleId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<NavRoute.WorkoutSession> { backStackEntry ->
            val workoutSession: NavRoute.WorkoutSession = backStackEntry.toRoute()
            OCEBVWorkoutSessionScreen(
                workoutId = workoutSession.workoutId,
                type = workoutSession.type,
                durationMinutes = workoutSession.duration,
                onFinish = { navController.popBackStack() }
            )
        }
    }
}
