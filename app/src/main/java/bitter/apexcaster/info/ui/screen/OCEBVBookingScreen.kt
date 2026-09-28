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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import bitter.apexcaster.info.ui.viewmodel.OCEBVBookingViewModel

@Composable
fun OCEBVBookingScreen(
    viewModel: OCEBVBookingViewModel = koinViewModel(),
    onBookingConfirmed: () -> Unit
) {
    val trainingType by viewModel.trainingType.collectAsState()
    val environment by viewModel.environment.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val durationMinutes by viewModel.durationMinutes.collectAsState()

    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(true) { viewModel.bookingEvent.collect { onBookingConfirmed() } }
    LaunchedEffect(Unit) { delay(80); startAnimation = true }

    val headerAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "HAlpha"
    )
    val headerOffset by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -32f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "HOffset"
    )
    val dividerWidth by animateDpAsState(
        targetValue = if (startAnimation) 120.dp else 0.dp,
        animationSpec = tween(700, delayMillis = 200, easing = FastOutSlowInEasing),
        label = "DivW"
    )

    val inf = rememberInfiniteTransition(label = "booking_ambient")
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
    val boltPulse by inf.animateFloat(
        1f, 1.14f,
        infiniteRepeatable(tween(1600, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "bolt"
    )

    //[@AGENT][Form for scheduling a future training session. The first LaunchedEffect above
    // already handles success: the ViewModel emits bookingEvent after the insert and we
    // navigate away — build only the form here.
    // Scrollable Column over the standard ambient backdrop (SpeedParticles(particleDrift),
    // blurred glow scaled by glowPulse, scanLine band). Tapping the background should clear
    // focus, so wrap the root in a rippleless clickable driving LocalFocusManager.
    // Header (headerAlpha / headerOffset): a circular icon well pulsing with boltPulse, the
    // screen title and a one-line subtitle, closed by a rule growing to dividerWidth.
    // Fields, each wrapped in MonoFormField with a sequential index so they cascade in. Every
    // field is fully controlled: it renders the state collected above and writes every edit
    // straight back to the ViewModel, which owns the form state — keep no local copies.
    //  0. MonoDropdownInput for the activity type — shows `trainingType`, reports through
    //     viewModel.onTrainingTypeChange(...). The option list must match the types the
    //     ViewModel's calculateStats() knows about, since it derives distance and calories
    //     from them.
    //  1. MonoDropdownInput for the environment the session takes place in — shows
    //     `environment`, reports through viewModel.onEnvironmentChange(...).
    //  2. MonoReadonlyField for the date, showing `selectedDate` in a readable format; tapping
    //     the field or its trailing icon opens a platform DatePickerDialog seeded from the
    //     current value, reporting back through viewModel.onDateChange(...).
    //  3. MonoTextField for the duration in minutes, with a numeric keyboard — shows
    //     `durationMinutes`, reports through viewModel.onDurationChange(...), which itself
    //     rejects non-digit input, so no filtering is needed here.
    //  4. MonoTextField for optional notes, multi-line via a minimum height — shows `notes`,
    //     reports through viewModel.onNotesChange(...).
    // Finally MonoSubmitButton calling viewModel.submitBooking(). Note the ViewModel ignores
    // the call unless both type and environment are chosen. Leave bottom padding clear of the
    // floating bottom bar and pin SportTagStrip at the bottom.]
}

@Composable
fun MonoFormField(index: Int, isVisible: Boolean, content: @Composable () -> Unit) {
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 200 + index * 90),
        label = "FA$index"
    )
    val offset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 18f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
        label = "FO$index"
    )

    //[@AGENT][Generic staggered-entrance wrapper for one form row. Render `content()` inside a
    // Box whose graphicsLayer applies translationY = offset and the fading alpha, so fields
    // cascade in one after another according to their index. Purely presentational.]
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonoTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    minHeight: Dp = Dp.Unspecified
) {
    //[@AGENT][Standard editable field: a full-width rounded OutlinedTextField using the shared
    // monoTextFieldColors(), with the label styled as a small tracked caption. When minHeight
    // is specified, apply heightIn(min) so the field grows into a multi-line note box.]
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonoReadonlyField(
    label: String,
    value: String,
    trailingIcon: ImageVector,
    onTrailingClick: () -> Unit,
    onClick: () -> Unit
) {
    //[@AGENT][Non-editable field that opens an external picker — styled identically to
    // MonoTextField but readOnly, with the given trailing icon calling onTrailingClick.
    // A readOnly OutlinedTextField still swallows taps, so overlay a transparent Box with
    // matchParentSize().clickable { onClick() } to make the whole row tappable.]
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonoDropdownInput(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(200),
        label = "Arrow"
    )

    //[@AGENT][Single-choice selector. A readOnly OutlinedTextField showing selectedOption, with
    // a trailing dropdown arrow rotated by arrowRotation so it flips as the menu opens, plus
    // the same full-area transparent tap overlay used by MonoReadonlyField.
    // Opening it reveals a DropdownMenu on surface with a hairline border,
    // listing each option — onSurface when it is the current selection, on a highlighted
    // inset row, onSurfaceVariant otherwise — separated by faint dividers except after the last
    // item. Choosing an option reports it through onOptionSelected and collapses the menu.]
}

@Composable
fun MonoSubmitButton(isVisible: Boolean, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 680),
        label = "SBAlpha"
    )
    val scale by animateFloatAsState(
        targetValue = when {
            !isVisible -> 0.82f; isPressed -> 0.96f; else -> 1f
        },
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "SBScale"
    )

    val inf = rememberInfiniteTransition(label = "btn_shimmer")
    val shimmer by inf.animateFloat(
        -1f, 2f,
        infiniteRepeatable(tween(2800, easing = LinearEasing)),
        label = "bs"
    )

    //[@AGENT][The form's primary action — a full-width accent block filled with `primary` and
    // content in `onPrimary`, tall enough to read as the page's main call to action,
    // entering last via alpha/scale and swept by an `onPrimary` `shimmer` band. It pairs a
    // confirmation icon with the submit label in heavy tracked type. Drive isPressed from
    // detectTapGestures and fire onClick on release so the button springs before it acts.]
}

//[@AGENT][Shared field styling used by every input on this screen. Fill in the arguments: the
// `surface` as the container in both focused and unfocused states, `onSurface` text with a
// `primary` cursor, `onSurfaceVariant` labels, and indicator lines plus trailing icons that
// move from `outline` towards `primary` when the field gains focus.]
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun monoTextFieldColors(): TextFieldColors = TextFieldDefaults.colors()
