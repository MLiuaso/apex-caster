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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVWorkoutSessionViewModel

@Composable
fun OCEBVWorkoutSessionScreen(
    workoutId: Long,
    type: String,
    durationMinutes: Int,
    viewModel: OCEBVWorkoutSessionViewModel = koinViewModel(),
    onFinish: () -> Unit
) {
    var timeLeftSeconds by remember { mutableStateOf(durationMinutes * 60) }
    var isRunning by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { delay(80); startAnimation = true }

    // Countdown: re-triggers each tick while running, and latches finished at zero.
    LaunchedEffect(isRunning, timeLeftSeconds) {
        if (isRunning && timeLeftSeconds > 0) {
            delay(1000L); timeLeftSeconds--
        } else if (timeLeftSeconds == 0) {
            isFinished = true; isRunning = false
        }
    }

    val minutes = timeLeftSeconds / 60
    val seconds = timeLeftSeconds % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)
    val progress = 1f - timeLeftSeconds.toFloat() / (durationMinutes * 60).toFloat()

    val headerAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "HA"
    )
    val headerOffset by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -30f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "HO"
    )
    val timerScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.72f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "TS"
    )
    val controlsAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(600, delayMillis = 420),
        label = "CA"
    )
    val backAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(600, delayMillis = 200),
        label = "BA"
    )

    val inf = rememberInfiniteTransition(label = "session_ambient")

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
    val outerRingRot by inf.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(22000, easing = LinearEasing)),
        label = "outer"
    )
    val innerRingRot by inf.animateFloat(
        360f, 0f,
        infiniteRepeatable(tween(14000, easing = LinearEasing)),
        label = "inner"
    )
    val glowPulse by inf.animateFloat(
        0.55f, 1f,
        infiniteRepeatable(tween(2800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "glow"
    )
    val timerPulse by inf.animateFloat(
        1f, 1.04f,
        infiniteRepeatable(tween(900, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse"
    )
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(3200, easing = LinearEasing)),
        label = "shimmer"
    )

    //[@AGENT][Immersive countdown timer for an in-progress workout. Full-screen, no bottom bar.
    // The countdown logic above is complete — build the presentation around it.
    // Ambient backdrop: SpeedParticles(particleDrift), a centered blurred radial glow scaled by
    // glowPulse, and the travelling scanLine band.
    // Header (headerAlpha / headerOffset): the workout `type` as a large heavy title with a
    // status badge whose text reflects running / paused / finished. Brighten the badge's fill
    // and border (animateColorAsState) while isRunning so running state is legible at a glance.
    // The centrepiece is a large circular dial scaled by timerScale, and additionally by
    // timerPulse only while isRunning so it breathes when the clock is live:
    //   - two counter-rotating orbit-dot rings (outerRingRot / innerRingRot) that are visibly
    //     brighter while isRunning and dim when paused;
    //   - a circular progress arc starting at -90 degrees, sweeping 360 * `progress`, stroked
    //     with a sweepGradient over a faint full-circle track;
    //   - an inner circular face (radial surfaceVariant-to-surface fill, sweepGradient border) swept
    //     by `shimmer`, showing formattedTime as the largest type on the screen, a short
    //     gradient rule, and a small caption labelling it.
    // Controls, faded in by controlsAlpha: two MonoControlButtons — a filled play/pause toggle
    // flipping isRunning, and an outlined stop button that calls viewModel.finishWorkout(workoutId)
    // then onFinish(). Finishing is what marks the workout completed so it reaches History and
    // Statistics — see WorkoutRepository. Plus SportTagStrip at low alpha.
    // Finally a circular back button, faded in by backAlpha, which also calls onFinish(). This
    // one has a fixed position: anchor it to the top-start corner, and it MUST apply
    // windowInsetsPadding(WindowInsets.statusBars) before its own padding, otherwise it
    // collides with the status bar in edge-to-edge mode.]
}

@Composable
fun MonoControlButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    filled: Boolean,
    isVisible: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = when {
            !isVisible -> 0.75f; isPressed -> 0.90f; else -> 1f
        },
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "CBS"
    )

    val inf = rememberInfiniteTransition(label = "btn_s")
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(2600, easing = LinearEasing)),
        label = "bs"
    )

    //[@AGENT][Large circular transport control, sized to be comfortably thumb-tappable, in two
    // variants.
    // filled = true: a radial fill built from `primary` with an `onPrimary` icon and a bright
    // sweepGradient border, swept by a `shimmer` band (the main action).
    // filled = false: radial `surfaceVariant`-to-`surface` fill with an `onSurface` icon and a
    // dimmer `outline` border.
    // Both spring via `scale` — pop in when isVisible turns true and compress on press. Drive
    // isPressed from detectTapGestures and fire onClick on release.]
}
