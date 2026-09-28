package bitter.apexcaster.info.ui.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import bitter.apexcaster.info.data.local.entity.OCEBVArticleEntity
import bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity
import bitter.apexcaster.info.ui.viewmodel.OCEBVHomeViewModel

@Composable
fun OCEBVHomeScreen(
    viewModel: OCEBVHomeViewModel = koinViewModel(),
    onArticleClick: (Long) -> Unit,
    onStartWorkoutClick: (OCEBVWorkoutEntity) -> Unit
) {
    val articles by viewModel.articles.collectAsState()
    val plannedWorkouts by viewModel.plannedWorkouts.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var workoutToDelete by remember { mutableStateOf<OCEBVWorkoutEntity?>(null) }
    var startAnimation by remember { mutableStateOf(false) }

    val headerAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "HeaderAlpha"
    )
    val headerOffsetY by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -40f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "HeaderOffset"
    )

    LaunchedEffect(Unit) { delay(80); startAnimation = true }

    val inf = rememberInfiniteTransition(label = "home_ambient")
    val particleDrift by inf.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(12000, easing = LinearEasing)),
        label = "drift"
    )
    val scanLine by inf.animateFloat(
        -1f, 1f,
        infiniteRepeatable(tween(6000, easing = LinearEasing)),
        label = "scan"
    )
    val glowPulse by inf.animateFloat(
        0.5f, 1f,
        infiniteRepeatable(tween(3200, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "glow"
    )

    //[@AGENT][The app's landing tab, vertically scrollable over the standard ambient backdrop
    // (SpeedParticles(particleDrift) + blurred radial glow scaled by glowPulse + travelling
    // scanLine band).
    // Sections, all inside one verticalScroll Column:
    //  - A greeting header pairing a small muted caption with a large greeting, closed by a
    //    gradient rule whose width tracks headerAlpha. Animate the block with headerAlpha /
    //    headerOffsetY.
    //  - MonoMotivationCard(startAnimation).
    //  - A MonoSectionHeader for the articles section, then a LazyRow of MonoArticleCard for
    //    `articles`, passing the item index so the cards stagger in. Tapping one calls
    //    onArticleClick(article.id).
    //  - A MonoSectionHeader for the planned-workouts section, then either an empty-state card
    //    when plannedWorkouts is empty, or a LazyRow of MonoWorkoutCard. Start invokes
    //    onStartWorkoutClick(workout); delete sets workoutToDelete and raises showDeleteDialog.
    //  - SportTagStrip as a reduced-alpha footer.
    // Leave generous bottom padding so the floating bottom bar never covers content.
    // When showDeleteDialog is true and workoutToDelete is set, show MonoDeleteDialog; confirm
    // calls viewModel.deleteWorkout(...) and both paths clear the state.
    // Note these are PLANNED (not yet completed) workouts — see WorkoutRepository.]
}

@Composable
private fun MonoSectionHeader(title: String, alpha: Float, offsetY: Float) {
    //[@AGENT][Section title: a heavy, widely tracked label in onSurface, aligned to
    // the screen gutter. Apply the passed alpha and a damped offsetY (about half the header's)
    // in a graphicsLayer so it trails the main header as it enters.]
}

@Composable
fun MonoMotivationCard(isVisible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(700, delayMillis = 200),
        label = "MotAlpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.88f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "MotScale"
    )

    val inf = rememberInfiniteTransition(label = "mot_inf")
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(3500, easing = LinearEasing)),
        label = "mot_shimmer"
    )
    val boltPulse by inf.animateFloat(
        1f, 1.12f,
        infiniteRepeatable(tween(1600, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bolt_pulse"
    )

    //[@AGENT][Hero card carrying a short editorial line (a motivational quote, tip of the day
    // or similar). A full-width rounded panel on surface, entering with
    // alpha/scale and continuously swept by the `shimmer` highlight band. It pairs an
    // `onSurfaceVariant` caption and the emphasised line with a circular icon well (radial
    // `surfaceVariant`-to-`surface` fill, sweepGradient border) holding a `primary` icon that
    // throbs with boltPulse.]
}

@Composable
fun MonoWorkoutCard(
    workout: OCEBVWorkoutEntity,
    onStartClick: () -> Unit,
    onDeleteClick: () -> Unit,
    index: Int,
    isVisible: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 300 + index * 90),
        label = "WAlpha"
    )
    val scale by animateFloatAsState(
        targetValue = when {
            !isVisible -> 0.82f; isPressed -> 0.96f; else -> 1f
        },
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "WScale"
    )

    //[@AGENT][Card for one planned workout in Home's horizontal carousel — a fixed-width tile.
    // `index` staggers its entrance (already wired into alpha) and isPressed must be driven by
    // detectTapGestures so the card springs inward on touch. It contains:
    //  - the workout's imageUrl via AsyncImage over a placeholder fill, darkened by a scrim,
    //    carrying the workout type as a pill badge and a small circular delete button calling
    //    onDeleteClick;
    //  - an `onSurfaceVariant` caption combining duration and environment, and the optional
    //    note on one line;
    //  - a full-width accent button filled with `primary`, content in `onPrimary`, with a
    //    forward arrow calling onStartClick.]
}

@Composable
fun MonoArticleCard(
    article: OCEBVArticleEntity,
    onClick: () -> Unit,
    index: Int,
    isVisible: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 400 + index * 90),
        label = "AAlpha"
    )
    val scale by animateFloatAsState(
        targetValue = when {
            !isVisible -> 0.82f; isPressed -> 0.96f; else -> 1f
        },
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "AScale"
    )

    //[@AGENT][Card for one article in the discover carousel — a fixed-size portrait tile with
    // the same staggered entrance and press-spring as MonoWorkoutCard. A cover image with a
    // scrim carries the article category as a pill badge, accompanied by the title in bold
    // `onSurface` and the subtitle in `onSurfaceVariant`, both clamped to a couple of lines. The
    // whole card is tappable via detectTapGestures, whose onTap invokes onClick.]
}

@Composable
fun MonoDeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    //[@AGENT][Shared destructive-confirmation dialog, also used by HistoryScreen. A Material3
    // AlertDialog restyled to the app: rounded container on surface, a heavy
    // title asking the user to confirm and a muted body line warning the action is permanent.
    // Build both buttons as custom Boxes rather than Material buttons — the destructive action
    // as a solid inverted block, the dismiss action as an outlined transparent block with muted
    // text.]
}
