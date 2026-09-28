package bitter.apexcaster.info.ui.screen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity
import bitter.apexcaster.info.ui.viewmodel.OCEBVHistoryViewModel

@Composable
fun OCEBVHistoryScreen(viewModel: OCEBVHistoryViewModel = koinViewModel()) {
    val workouts by viewModel.workouts.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var workoutToDelete by remember { mutableStateOf<OCEBVWorkoutEntity?>(null) }
    var startAnimation by remember { mutableStateOf(false) }

    val headerAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "HA"
    )
    val headerOffset by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -36f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "HO"
    )
    val dividerWidth by animateDpAsState(
        targetValue = if (startAnimation) 100.dp else 0.dp,
        animationSpec = tween(700, delayMillis = 200, easing = FastOutSlowInEasing),
        label = "DivW"
    )

    LaunchedEffect(Unit) { delay(80); startAnimation = true }

    val inf = rememberInfiniteTransition(label = "history_ambient")
    val particleDrift by inf.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(13000, easing = LinearEasing)),
        label = "drift"
    )
    val scanLine by inf.animateFloat(
        -1f, 1f,
        infiniteRepeatable(tween(7000, easing = LinearEasing)),
        label = "scan"
    )
    val glowPulse by inf.animateFloat(
        0.5f, 1f,
        infiniteRepeatable(tween(3200, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "glow"
    )
    val iconPulse by inf.animateFloat(
        1f, 1.12f,
        infiniteRepeatable(tween(1700, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "icon"
    )

    //[@AGENT][Log of COMPLETED training sessions (the ViewModel already filters on
    // isCompleted = true — see WorkoutRepository; never show planned ones here).
    // Standard ambient backdrop: SpeedParticles(particleDrift), a blurred radial glow scaled by
    // glowPulse, and the travelling scanLine band.
    // Header (animated with headerAlpha / headerOffset): a circular icon well throbbing with
    // iconPulse, the screen title and a subtitle reporting how many sessions are recorded,
    // closed by a gradient rule that grows to dividerWidth.
    // Body: when the list is empty, an empty state pairing a large circular placeholder holding
    // a faded icon with a short muted message. Otherwise a LazyColumn of
    // MonoHistoryWorkoutCard, passing the index so cards stagger in, closing with SportTagStrip
    // and enough bottom padding to clear the floating bottom bar.
    // Deleting a card sets workoutToDelete and raises showDeleteDialog; when both are set show
    // MonoDeleteDialog, whose confirm calls viewModel.deleteWorkout(...) and clears the state.]
}

@Composable
fun MonoHistoryWorkoutCard(
    workout: OCEBVWorkoutEntity,
    onDeleteClick: () -> Unit,
    index: Int,
    isVisible: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 180 + index * 75),
        label = "CA$index"
    )
    val scale by animateFloatAsState(
        targetValue = when {
            !isVisible -> 0.88f; isPressed -> 0.97f; else -> 1f
        },
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "CS$index"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (isVisible) 0f else 28f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "CO$index"
    )

    val inf = rememberInfiniteTransition(label = "card_s_$index")
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(3800 + index * 200, easing = LinearEasing)),
        label = "s$index"
    )

    //[@AGENT][Full-width card for one finished session. Enters with alpha + scale + upward
    // offsetY staggered by index, is swept by the looping `shimmer` highlight, and springs
    // inward on press via detectTapGestures driving isPressed.
    // It contains the workout image with a scrim, carrying the workout type as a pill badge and
    // a circular delete button calling onDeleteClick; the session's date and time formatted for
    // reading, alongside a duration badge; a hairline gradient divider; a small dot marker with
    // the environment label; and the note clamped to a couple of lines when present.]
}
