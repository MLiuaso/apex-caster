package bitter.apexcaster.info.ui.approot

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination



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
        targetValue = if (isSelected) 80.dp else 48.dp,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium),
        label = "ncw_${item.title}"
    )

    //[@AGENT][A single tab. Selecting it must feel alive — wire up every value above:
    // the item widens via containerWidth when selected; the icon takes `primary` when selected
    // and `onSurfaceVariant` at reduced alpha otherwise, scaled by iconScale so it breathes;
    // when selected, a rotating orbit-dot ring (ringRot) and a circular `surfaceVariant` well
    // with a sweepGradient border fade in behind the icon, and the item title joins it as a
    // very small tracked caption, faded by labelAlpha and slid in via labelOffset.
    // Use clickable with no ripple (indication = null) — the scale and glow are the feedback.]
}
