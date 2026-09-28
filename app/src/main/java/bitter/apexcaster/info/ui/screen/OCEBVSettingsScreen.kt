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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun OCEBVSettingsScreen() {
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

    val inf = rememberInfiniteTransition(label = "settings_ambient")
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
        infiniteRepeatable(tween(28000, easing = LinearEasing)),
        label = "ring"
    )

    //[@AGENT][Settings tab. Stateless — it has no ViewModel because the app currently exposes
    // no editable preferences (the only stored flag, onboarding completion, is not user-facing).
    // Scrollable Column over the standard ambient backdrop (SpeedParticles(particleDrift),
    // blurred radial glow scaled by glowPulse, travelling scanLine band).
    // Header (headerAlpha / headerOffset): an orbit-dot ring rotating with outerRingRot around
    // a circular icon well throbbing with iconPulse, the screen title and a subtitle, closed by
    // a rule growing to dividerWidth.
    // Body: a MonoSectionLabel introducing the about section, followed by
    // MonoAboutCard(startAnimation).
    // As real preferences are added, group them under further MonoSectionLabel headings and
    // render each row with MonoSettingsItem, giving each an increasing `delay` so the list
    // cascades in. Leave bottom padding clear of the floating bottom bar.]
}

@Composable
fun MonoSectionLabel(text: String, isVisible: Boolean, delay: Int) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = delay),
        label = "SLA"
    )

    //[@AGENT][Group heading for a settings section: `text` as a small heavily tracked caption in
    // onSurfaceVariant, accompanied by a hairline rule taking up the remaining width and fading
    // out. Fade the whole heading in with the `alpha` above, staggered by `delay`.]
}

@Composable
fun MonoAboutCard(isVisible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 380),
        label = "ACA"
    )
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.94f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "ACS"
    )

    val inf = rememberInfiniteTransition(label = "about_shimmer")
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "as"
    )

    //[@AGENT]["About" panel. Standard rounded card on surface with a hairline
    // border, entering with alpha/scale and swept by the looping `shimmer` band.
    // It contains a small circular icon well with the app wordmark; a hairline gradient
    // divider; a short paragraph in onSurfaceVariant describing what the app does; and a small
    // bordered chip showing the version string.]
}

@Composable
fun MonoSettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isVisible: Boolean,
    delay: Int,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = delay),
        label = "SIA"
    )
    val scale by animateFloatAsState(
        targetValue = when {
            !isVisible -> 0.94f; isPressed -> 0.97f; else -> 1f
        },
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "SIS"
    )

    val inf = rememberInfiniteTransition(label = "item_shimmer")
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(3600, easing = LinearEasing)),
        label = "is"
    )

    //[@AGENT][Reusable tappable settings row — currently unused, kept as the building block for
    // future preference entries. A rounded card on surface, entering with
    // alpha/scale staggered by `delay` and swept by `shimmer`; brighten its border while
    // pressed. It groups a circular icon well, `title` in bold `onSurface` with `subtitle` in
    // onSurfaceVariant, and a chevron marking it as navigable. Drive isPressed from
    // detectTapGestures, firing onClick from onTap.]
}
