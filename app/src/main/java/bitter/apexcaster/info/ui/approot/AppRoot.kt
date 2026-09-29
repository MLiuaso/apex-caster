package bitter.apexcaster.info.ui.approot

import android.app.Activity
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
fun AppBottomBar(
    items: List<BottomNavItem>,
    currentDestination: NavDestination?,
    onNavigateToRoute: (BottomNavItem) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // М'яке розмите світіння позаду панелі
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .blur(20.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.25f), CircleShape)
        )

        // Сама панель у формі капсули
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, CircleShape, spotColor = MaterialTheme.colorScheme.primary)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.1f)
                        )
                    ),
                    shape = CircleShape
                )
                .padding(horizontal = 12.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentDestination.matchesAnyRoute(listOf(item.route::class))
                AppBottomBarItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onNavigateToRoute(item) }
                )
            }
        }
    }
}

@Composable
private fun AppBottomBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val inf = rememberInfiniteTransition(label = "nav_${item.title}")

    val pulse by inf.animateFloat(
        1f, 1.06f,
        infiniteRepeatable(
            tween(1400, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "np_${item.title}"
    )
    val ringRot by inf.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(8000, easing = LinearEasing)),
        label = "nr_${item.title}"
    )

    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) pulse else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium),
        label = "nis_${item.title}"
    )
    val labelAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(250),
        label = "nla_${item.title}"
    )
    val labelOffset by animateFloatAsState(
        targetValue = if (isSelected) 0f else 6f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "nlo_${item.title}"
    )
    val containerWidth by animateDpAsState(
        targetValue = if (isSelected) 72.dp else 48.dp,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium),
        label = "ncw_${item.title}"
    )

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .width(containerWidth)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(48.dp)
        ) {
            if (isSelected) {
                // Background well and sweeping gradient outline
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationZ = ringRot }
                        .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        .border(
                            width = 1.dp,
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0f),
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                                )
                            ),
                            shape = CircleShape
                        )
                ) {
                    // Small orbital dot to make the rotation visible
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 2.dp)
                            .size(3.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                    )
                }
            }

            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.scale(iconScale)
            )
        }

        if (labelAlpha > 0f) {
            Text(
                text = item.title.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .graphicsLayer {
                        alpha = labelAlpha
                        translationY = labelOffset
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun AppRoot() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val shouldShowBottomBar = !currentDestination.matchesAnyRoute(bottomBarHiddenScreens)

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