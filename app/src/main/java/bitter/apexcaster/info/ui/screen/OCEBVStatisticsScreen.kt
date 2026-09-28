package bitter.apexcaster.info.ui.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVStatisticsViewModel

@Composable
fun OCEBVStatisticsScreen(viewModel: OCEBVStatisticsViewModel = koinViewModel()) {
    val totalWorkouts by viewModel.totalWorkouts.collectAsState()
    val totalDuration by viewModel.totalDurationMinutes.collectAsState()
    val workoutsByType by viewModel.workoutsByType.collectAsState()

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
        targetValue = if (startAnimation) 110.dp else 0.dp,
        animationSpec = tween(700, delayMillis = 200, easing = FastOutSlowInEasing),
        label = "DivW"
    )
    val sectionAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(600, delayMillis = 580),
        label = "SA"
    )

    LaunchedEffect(Unit) { delay(80); startAnimation = true }

    val inf = rememberInfiniteTransition(label = "stats_ambient")
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
    val outerRingRot by inf.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(25000, easing = LinearEasing)),
        label = "outer"
    )

    //[@AGENT][Aggregated training statistics, scrollable over the standard ambient backdrop
    // (SpeedParticles(particleDrift), blurred radial glow scaled by glowPulse, scanLine band).
    // All figures come from COMPLETED sessions only — see WorkoutRepository.
    // Header (animated with headerAlpha / headerOffset): an orbit-dot ring rotating with
    // outerRingRot around a circular icon well throbbing with iconPulse, the screen title and a
    // subtitle, closed by a rule growing to dividerWidth.
    // Then two equal-weight MonoStatsCard tiles sharing a row — session count (totalWorkouts)
    // and total minutes (totalDuration) — given index 0 and 1 so they cascade.
    // Then a section label for the per-type breakdown, faded in by sectionAlpha, followed by
    // MonoBreakdownCard(workoutsByType, startAnimation). Close with SportTagStrip and enough
    // bottom padding that the floating bottom bar never overlaps the content.]
}

@Composable
fun MonoStatsCard(
    title: String,
    value: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    index: Int
) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 280 + index * 130),
        label = "SCA$index"
    )
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.78f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "SCS$index"
    )
    val animatedValue by animateIntAsState(
        targetValue = if (isVisible) value else 0,
        animationSpec = tween(1100, delayMillis = 380 + index * 130, easing = FastOutSlowInEasing),
        label = "SCV$index"
    )

    val inf = rememberInfiniteTransition(label = "sc_inf_$index")
    val iconPulse by inf.animateFloat(
        1f, 1.10f,
        infiniteRepeatable(
            tween(1600 + index * 200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "scp$index"
    )
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(3400 + index * 300, easing = LinearEasing)),
        label = "scs$index"
    )

    //[@AGENT][A single headline metric tile. Rounded `surface` panel with a hairline `outline`
    // border, entering with alpha/scale staggered by index and continuously swept by `shimmer`.
    // It groups a circular icon well (radial `surfaceVariant`-to-`surface` fill, sweepGradient
    // border) whose `primary` icon throbs with iconPulse; the number rendered from
    // animatedValue at display scale so it counts up from zero rather than appearing instantly;
    // a short gradient rule; and the `title` as a small tracked `onSurfaceVariant` caption.]
}

@Composable
fun MonoBreakdownCard(workoutsByType: Map<String, Int>, isVisible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 680),
        label = "BCA"
    )
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.94f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "BCS"
    )

    val inf = rememberInfiniteTransition(label = "bc_shimmer")
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(4200, easing = LinearEasing)),
        label = "bcs"
    )

    //[@AGENT][Panel breaking sessions down by activity type. Same card styling as
    // MonoStatsCard, entering with alpha/scale and swept by `shimmer`.
    // When the map is empty, show an inline empty state pairing a circular `surfaceVariant`
    // well holding a faded icon with a short `onSurfaceVariant` caption.
    // Otherwise take maxCount = the largest value and render one MonoProgressRow per entry,
    // passing the index for stagger, separated by hairline gradient dividers (no divider after
    // the last row).]
}

@Composable
fun MonoProgressRow(
    type: String,
    count: Int,
    maxCount: Int,
    index: Int,
    isVisible: Boolean,
    totalRows: Int
) {
    val progress by animateFloatAsState(
        targetValue = if (isVisible) count / maxCount.toFloat() else 0f,
        animationSpec = tween(1100, delayMillis = 780 + index * 90, easing = FastOutSlowInEasing),
        label = "PR$index"
    )
    val animatedCount by animateIntAsState(
        targetValue = if (isVisible) count else 0,
        animationSpec = tween(900, delayMillis = 780 + index * 90, easing = FastOutSlowInEasing),
        label = "PC$index"
    )

    //[@AGENT][One activity type inside the breakdown. Pairs a small dot marker and the `type`
    // label with a bordered badge showing animatedCount so the number counts up, over a slim
    // rounded `surfaceVariant` track whose fill is sized by fillMaxWidth(progress) and painted
    // with a gradient built from `primary`, so each bar sweeps out from zero on entry. Both
    // animations are staggered by index — keep them wired.]
}
