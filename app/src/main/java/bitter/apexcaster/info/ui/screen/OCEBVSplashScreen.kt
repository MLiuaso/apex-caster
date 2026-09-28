package bitter.apexcaster.info.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVOCEBVSplashVM

//[@AGENT][This file also owns the small decorative composables shared by every other screen
// (SpeedParticles, SportTagStrip, LoadingDots). Keep them here so screens can reuse them.
// See Theme.kt for the global visual and motion language they belong to.]

@Composable
fun OCEBVSplashScreen(
    viewModel: OCEBVOCEBVSplashVM = koinViewModel(),
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState()

    // Hand-choreographed intro: each element is its own Animatable so they can be staggered
    // independently in the LaunchedEffect below.
    val logoScale = remember { Animatable(0.3f) }
    val logoAlpha = remember { Animatable(0f) }
    val logoOffsetY = remember { Animatable(80f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val subtitleOffsetY = remember { Animatable(24f) }
    val lineWidth = remember { Animatable(0f) }
    val tagAlpha = remember { Animatable(0f) }

    LaunchedEffect(true) {
        launch { logoAlpha.animateTo(1f, tween(700, easing = LinearOutSlowInEasing)) }
        launch { logoOffsetY.animateTo(0f, tween(800, easing = FastOutSlowInEasing)) }
        launch {
            logoScale.animateTo(
                1f, spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)
            )
        }
        launch {
            delay(350)
            subtitleAlpha.animateTo(1f, tween(600))
            subtitleOffsetY.animateTo(0f, tween(600, easing = FastOutSlowInEasing))
        }
        launch { delay(500); lineWidth.animateTo(1f, tween(900, easing = FastOutSlowInEasing)) }
        launch { delay(800); tagAlpha.animateTo(1f, tween(500)) }
    }

    LaunchedEffect(isLoading) {
        if (!isLoading) {
            delay(400)
            if (isOnboardingCompleted) onNavigateToHome() else onNavigateToOnboarding()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "ambient")

    val outerRingRot by infiniteTransition.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(22000, easing = LinearEasing)),
        label = "outer_ring"
    )
    val innerRingRot by infiniteTransition.animateFloat(
        360f, 0f,
        infiniteRepeatable(tween(15000, easing = LinearEasing)),
        label = "inner_ring"
    )
    val particlePhase by infiniteTransition.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(7000, easing = LinearEasing)),
        label = "particles"
    )
    val glowPulse by infiniteTransition.animateFloat(
        0.5f, 1f,
        infiniteRepeatable(tween(2600, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "glow_pulse"
    )
    val iconShimmer by infiniteTransition.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(3200, easing = LinearEasing)),
        label = "icon_shimmer"
    )
    val float by infiniteTransition.animateFloat(
        -6f, 6f,
        infiniteRepeatable(tween(3800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "float"
    )
    val scanLine by infiniteTransition.animateFloat(
        -1f, 1f,
        infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "scan"
    )
    val particleDrift by infiniteTransition.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(10000, easing = LinearEasing)),
        label = "drift"
    )

    //[@AGENT][Branded launch screen and the app's routing gate. The second LaunchedEffect above
    // already owns navigation: once the ViewModel finishes loading it sends the user to Home or
    // Onboarding — build only the visuals here.
    // Over the ambient backdrop (SpeedParticles(particleDrift), a large blurred radial glow
    // scaled by glowPulse, and the travelling scanLine band), build a centred composition:
    // counter-rotating orbit-dot rings driven by outerRingRot / innerRingRot, a ring of radial
    // tick marks, and OrbitingSpeedParticles(particlePhase) tracing the circle.
    // The brand content sits at the centre, with the whole intro choreography applied through a
    // graphicsLayer — translationY = logoOffsetY + float (so it keeps bobbing after landing),
    // scale = logoScale, alpha = logoAlpha. It comprises SportMedallion(iconShimmer, glowPulse),
    // the app wordmark as the largest and most heavily tracked type on screen, a gradient rule
    // whose width grows with lineWidth, a short tagline caption offset by subtitleOffsetY /
    // subtitleAlpha, and LoadingDots(tagAlpha).
    // Include SportTagStrip as the footer, faded in by subtitleAlpha.]
}

@Composable
fun SportMedallion(shimmer: Float, glowPulse: Float) {
    //[@AGENT][The app's logo mark — a large circular emblem. A soft radial halo whose alpha
    // follows glowPulse, a circular badge with a radial surfaceVariant-to-surface fill and a thick
    // sweepGradient border, and a Canvas drawing the emblem itself: a thin circle outline, a
    // few radial tick marks, and the brand glyph as a Path filled with a gradient over a wider
    // translucent stroke. Brighten the glyph and its stroke as `shimmer` sweeps past.
    // Substitute whatever mark suits the app.]
}

@Composable
fun LoadingDots(alpha: Float) {
    //[@AGENT][A few small circular dots forming a generic busy indicator (also shown by
    // ArticleDetailScreen while an article loads). Drive each dot from one
    // rememberInfiniteTransition, animating its alpha and scale on a short reversing tween and
    // staggering the delay by index so the pulse travels across the group. The `alpha`
    // parameter fades the whole indicator.]
}

@Composable
fun OrbitingSpeedParticles(phase: Float) {
    //[@AGENT][Splash-only flourish: a Canvas ring of a handful of dots orbiting the logo mark.
    // Position each at `phase` plus an even angular offset, and trail each with a short comet
    // tail of progressively smaller, fainter dots drawn at slightly earlier angles.]
}

@Composable
fun SpeedParticles(phase: Float) {
    //[@AGENT][Shared ambient particle field — the bottom layer of every screen's background.
    // Remember a fixed list of randomly placed particles, each with its own speed, so the field
    // is stable across recomposition. In a full-size Canvas, scroll each particle horizontally
    // by `phase` * its speed and wrap around the edge. Draw the faster ones as short
    // motion-blur streaks and the slower ones as dots, all in onSurface at very low
    // alpha oscillating with phase. It must stay subtle — texture, not decoration.]
}

@Composable
fun SportTagStrip(modifier: Modifier = Modifier) {
    //[@AGENT][Shared footer motif: a short list of themed keywords as tiny muted captions
    // separated by faint interpuncts, with hairline gradient rules trailing off towards the
    // screen edges. Supply the keyword list per app. Used as a low-alpha footer on most
    // screens.]
}
