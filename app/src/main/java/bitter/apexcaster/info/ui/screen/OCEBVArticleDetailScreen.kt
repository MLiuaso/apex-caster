package bitter.apexcaster.info.ui.screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import bitter.apexcaster.info.data.local.entity.OCEBVArticleEntity
import bitter.apexcaster.info.ui.viewmodel.OCEBVArticleDetailViewModel

@Composable
fun OCEBVArticleDetailScreen(
    articleId: Long,
    viewModel: OCEBVArticleDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val article by viewModel.article.collectAsState()
    val scrollState = rememberScrollState()
    var startAnim by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { delay(80); startAnim = true }

    // Parallax: the hero image zooms slightly and fades out as the sheet scrolls over it.
    val scrollOffset = scrollState.value.toFloat()
    val imageScale = 1f + (scrollOffset / 2200f).coerceIn(0f, 0.25f)
    val imageAlpha = (1f - (scrollOffset / 450f)).coerceIn(0f, 1f)

    val backAlpha by animateFloatAsState(
        targetValue = if (startAnim) 1f else 0f,
        animationSpec = tween(600, delayMillis = 200),
        label = "BackAlpha"
    )
    val backScale by animateFloatAsState(
        targetValue = if (startAnim) 1f else 0.75f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "BackScale"
    )

    val inf = rememberInfiniteTransition(label = "detail_ambient")
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

    //[@AGENT][Full-screen reader for one article, opened from Home's DISCOVER carousel. No
    // bottom bar. The ViewModel resolves the article from the nav argument, so `articleId` is
    // only context here.
    // While `article` is null, show a loading state: SpeedParticles plus LoadingDots and a
    // short muted caption.
    // Once loaded, a vertically scrolling Column over the ambient backdrop
    // (SpeedParticles(particleDrift) + the scanLine band):
    //   - a tall hero image (AsyncImage of article.imageUrl) in a graphicsLayer using
    //     imageScale and imageAlpha so it zooms and dissolves on scroll, overlaid by a scrim
    //     and a faded orbit-dot ring;
    //   - MonoContentSection(article, startAnim), which overlaps the image.
    // Include a circular back button entering with backScale/backAlpha and calling onBackClick.
    // This one has a fixed position: anchor it to the top-start corner, and it MUST apply
    // windowInsetsPadding(WindowInsets.statusBars) before its own padding or it will overlap
    // the status bar in edge-to-edge mode.]
}

@Composable
private fun MonoContentSection(
    item: OCEBVArticleEntity,
    isVisible: Boolean
) {
    val contentAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(800, delayMillis = 250),
        label = "CAlpha"
    )
    val contentOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 48f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "COffset"
    )
    val badgeScale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.75f,
        animationSpec = tween(600, delayMillis = 380, easing = FastOutSlowInEasing),
        label = "BadgeScale"
    )
    val dividerWidth by animateDpAsState(
        targetValue = if (isVisible) 80.dp else 0.dp,
        animationSpec = tween(700, delayMillis = 620, easing = FastOutSlowInEasing),
        label = "DividerW"
    )

    val inf = rememberInfiniteTransition(label = "card_shimmer")
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "shimmer"
    )

    //[@AGENT][The article body, presented as a sheet that overlaps the hero image: offset so it
    // rides over the image with its leading corners rounded, filled with background,
    // a gradient border along the seam and a `shimmer` band sweeping across it.
    // Contents, entering together via contentAlpha and contentOffset, the text blocks rendered
    // with MonoAnimatedText and given increasing `delay` values so they cascade:
    //   - the article category as a bordered badge popping in with badgeScale;
    //   - the title at heading scale in heavy weight;
    //   - the subtitle one step down, in onSurfaceVariant;
    //   - a gradient rule that grows to dividerWidth;
    //   - the body copy at reading scale with relaxed line height;
    //   - a closing hairline divider and SportTagStrip at low alpha.]
}

@Composable
fun MonoAnimatedText(
    text: String,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    color: Color,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    isVisible: Boolean,
    delay: Int
) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(650, delayMillis = delay),
        label = "TAAlpha_$delay"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (isVisible) 0f else 22f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "TAOffset_$delay"
    )

    //[@AGENT][Reusable text block that fades and slides up into place. Render a Text with the
    // passed typography, wrapped in a graphicsLayer applying translationY = offsetY and the
    // alpha above. Callers stagger successive blocks by passing increasing `delay` values, so
    // paragraphs cascade rather than appearing at once.]
}
