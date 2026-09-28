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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVOCEBVOnboardingVM
import kotlin.math.absoluteValue

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val accentAlpha: Float = 0.12f
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OCEBVOnboardingScreen(
    viewModel: OCEBVOCEBVOnboardingVM = koinViewModel(),
    onFinish: () -> Unit
) {
    val scope = rememberCoroutineScope()

    //[@AGENT][First-run intro. Reached from Splash only while the onboarding flag is false.
    // Start by defining the slides: a list of OnboardingPage (icon + title + description)
    // pitching the app's main capabilities — a handful of slides is typical. Drive
    // rememberPagerState's pageCount from that list's size rather than a literal.
    // A full-screen Box hosting AmbientBackground(), then a HorizontalPager over the pages,
    // leaving room for the controls. For each page compute its offset as
    // (currentPage - page) + currentPageOffsetFraction and hand it to OnboardingPageContent so
    // slides parallax against each other.
    // The controls are anchored to the bottom of the screen: PillIndicators(pageCount,
    // pagerState.currentPage), together with either — on the last page — a single full-width
    // filled MonoButton that completes the flow, or otherwise a MonoGhostButton to skip paired
    // with a filled MonoButton that animates the pager to the next page inside `scope`.
    // Both the finish and skip actions must call viewModel.completeOnboarding() before
    // onFinish(), so the intro is never shown again. Include SportTagStrip as the footer.]
}

@Composable
private fun AmbientBackground() {
    val inf = rememberInfiniteTransition(label = "bg_ambient")

    val outerRot by inf.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(30000, easing = LinearEasing)),
        label = "outer"
    )
    val innerRot by inf.animateFloat(
        360f, 0f,
        infiniteRepeatable(tween(20000, easing = LinearEasing)),
        label = "inner"
    )
    val particleDrift by inf.animateFloat(
        0f, 1f,
        infiniteRepeatable(tween(12000, easing = LinearEasing)),
        label = "drift"
    )
    val glow by inf.animateFloat(
        0.6f, 1f,
        infiniteRepeatable(tween(3000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "glow"
    )
    val scanLine by inf.animateFloat(
        -1f, 1f,
        infiniteRepeatable(tween(5000, easing = LinearEasing)),
        label = "scan"
    )

    //[@AGENT][This screen's backdrop, factored out because it sits behind the pager rather than
    // inside a page. Compose the standard ambient stack from the values above:
    // SpeedParticles(particleDrift), a large blurred radial glow breathing with `glow`, the
    // travelling scanLine band, and — centered and screen-sized — two large counter-rotating
    // orbit-dot rings driven by outerRot and innerRot. Everything stays at very low alpha.]
}

@Composable
fun OnboardingPageContent(page: OnboardingPage, pageOffset: Float) {
    var started by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (started) 1f else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "alpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (started) 1f else 0.72f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "scale"
    )
    val slideY by animateFloatAsState(
        targetValue = if (started) 0f else 40f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "slideY"
    )

    val inf = rememberInfiniteTransition(label = "page_inf")
    val float by inf.animateFloat(
        -8f, 8f,
        infiniteRepeatable(tween(3500, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "float"
    )
    val pulse by inf.animateFloat(
        1f, 1.06f,
        infiniteRepeatable(tween(1800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pulse"
    )
    val iconShimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "shimmer"
    )
    val ringRot by inf.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(18000, easing = LinearEasing)),
        label = "ring_rot"
    )

    // Replays the entrance every time this page becomes the centered one.
    LaunchedEffect(pageOffset) {
        if (pageOffset.absoluteValue < 0.5f) {
            delay(150)
            started = true
        } else {
            started = false
        }
    }

    //[@AGENT][One onboarding slide, laid out around its centre. Apply a graphicsLayer with a
    // translationX proportional to pageOffset and an alpha of (1 - |pageOffset|), so
    // neighbouring slides drift and fade as the pager moves.
    // The hero is the dominant element, entering with scale/alpha and permanently bobbing via
    // translationY = float: a soft radial halo, an orbit-dot ring rotating with ringRot, and a
    // circular surfaceVariant-to-surface badge breathing with `pulse` behind a sweepGradient border,
    // containing page.icon at large size with an `iconShimmer` highlight sweeping across it.
    // Accompanied by page.title as a heavy tracked heading, a short gradient rule whose width
    // tracks `alpha`, and page.description in onSurfaceVariant with relaxed line height. Offset
    // the title by slideY and the description by a slightly larger multiple of it so the text
    // staggers into place.]
}

@Composable
private fun PillIndicators(pageCount: Int, currentPage: Int) {
    //[@AGENT][Pager position indicator — one rounded pill per page. Animate each pill's width
    // with animateDpAsState (clearly elongated when selected, a small dot otherwise) using a
    // bouncy spring, and its color with animateColorAsState (onSurface when selected,
    // muted at low alpha otherwise), so the active pill visibly stretches as pages change.]
}

@Composable
private fun MonoButton(
    label: String,
    icon: ImageVector,
    filled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    //[@AGENT][Primary action button. A rounded Material3 Button with no elevation, showing the
    // label in heavy tracked type with a small accompanying icon. filled = true means a solid
    // `primary` fill with `onPrimary` content; otherwise transparent with `primary` content
    // and a hairline `outline` border.]
}

@Composable
private fun MonoGhostButton(label: String, onClick: () -> Unit) {
    //[@AGENT][Low-emphasis tertiary action (used to skip the flow). A plain TextButton showing
    // the label as a small tracked caption in onSurfaceVariant — no fill, no border.]
}
