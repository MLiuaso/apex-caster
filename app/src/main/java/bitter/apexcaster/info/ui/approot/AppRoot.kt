package bitter.apexcaster.info.ui.approot

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bitter.apexcaster.info.ui.navigation.AppNavHost
import bitter.apexcaster.info.ui.navigation.NavRoute
import kotlin.reflect.KClass

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: NavRoute,
)

private val navigationItems: List<BottomNavItem> = listOf(
    BottomNavItem(title = "Home", icon = Icons.Default.Home, route = NavRoute.Home),
    BottomNavItem(title = "Book", icon = Icons.Default.Add, route = NavRoute.Booking),
    BottomNavItem(title = "History", icon = Icons.Default.History, route = NavRoute.History),
    BottomNavItem(title = "Stats", icon = Icons.Default.BarChart, route = NavRoute.Statistics),
    BottomNavItem(title = "Settings", icon = Icons.Default.Settings, route = NavRoute.Settings),
)

private val bottomBarHiddenScreens: List<KClass<out NavRoute>> = listOf(
    NavRoute.Splash::class,
    NavRoute.Onboarding::class,
    NavRoute.ArticleDetail::class,
    NavRoute.WorkoutSession::class,
)

@Composable
fun AppRoot() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val shouldShowBottomBar = !currentDestination.matchesAnyRoute(bottomBarHiddenScreens)

    // Edge-to-edge. System bar icons follow the theme: dark icons on a light background,
    // light icons on a dark one, so the app works under either color scheme.
    val view = LocalView.current
    val useDarkSystemBarIcons = MaterialTheme.colorScheme.background.luminance() > 0.5f
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, view).apply {
                isAppearanceLightStatusBars = useDarkSystemBarIcons
                isAppearanceLightNavigationBars = useDarkSystemBarIcons
            }
        }
    }

    AppRootContent(
        navController = navController,
        currentDestination = currentDestination,
        shouldShowBottomBar = shouldShowBottomBar,
        onNavigateToRoute = { item ->
            navController.navigate(item.route) {
                popUpTo(NavRoute.Home) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
    )
}

@Composable
private fun AppRootContent(
    navController: NavHostController,
    currentDestination: NavDestination?,
    shouldShowBottomBar: Boolean,
    onNavigateToRoute: (BottomNavItem) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (shouldShowBottomBar) {
                    AppBottomBar(
                        items = navigationItems,
                        currentDestination = currentDestination,
                        onNavigateToRoute = onNavigateToRoute,
                    )
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(
                    bottom = innerPadding.calculateBottomPadding()
                ),
            )
        }
    }
}

fun NavDestination?.matchesAnyRoute(routes: List<KClass<out NavRoute>>): Boolean {
    return this?.let { destination ->
        routes.any { route -> destination.hasRoute(route) }
    } == true
}
