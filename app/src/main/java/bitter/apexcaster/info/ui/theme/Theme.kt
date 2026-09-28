package bitter.apexcaster.info.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

//[@AGENT][=============================================================================
// GLOBAL DESIGN & MOTION LANGUAGE — read this before implementing any screen.
// Every stripped composable in ui/screen and ui/approot assumes the vocabulary below.
// This is a template: all copy, icons, fonts, colors and exact sizes are per-app
// decisions. What follows describes structure and behaviour, with ranges as guidance.
// =============================================================================
//
// COLOR RULE (applies everywhere, no exceptions):
// Never hardcode a Color in UI code. Always read from MaterialTheme.colorScheme, so the
// app can be recoloured entirely from this file. See Color.kt for what each role means
// here. The palette should read as athletic and performance-oriented, with the accent
// (primary) reserved for action and progress. Derive emphasis with .copy(alpha = ...) on a
// role rather than inventing new colors.
//
// VISUAL LANGUAGE:
//  - Every screen is a full-size Box filled with `background`, drawn edge-to-edge under the
//    system bars. Anything anchored to a screen edge must apply
//    windowInsetsPadding(WindowInsets.statusBars or .navigationBars) so it never collides
//    with the status or navigation bar.
//  - Ambient backdrop, layered behind the content on every screen:
//      1. SpeedParticles(phase) — drifting particle field.
//      2. A large, heavily blurred radial glow at very low alpha, scaled by a slow
//         "glowPulse" so it breathes.
//      3. A full-size Canvas scanline: a faint horizontal band travelling vertically,
//         driven by a "scanLine" value.
//    Tint all three from a single role (primary for an energetic look, or onBackground for a
//    neutral one) at low alpha — they are texture and must never compete with the content.
//  - Orbit-dot rings are the signature motif: a Canvas ring of evenly spaced dots with
//    alternating high/low alpha, slowly rotated. Used around header icons, the session
//    timer, the splash mark and the selected navigation item. Counter-rotate a second,
//    smaller ring for depth.
//  - Cards: generously rounded, filled with `surface`, a hairline `outline` border at low
//    alpha, and a "shimmer" — a soft highlight band sweeping horizontally on a loop.
//  - Circular icon wells: a radial gradient fill from `surfaceVariant` towards `surface` or
//    `background`, with a sweepGradient border cycling a role against a lower-alpha copy of
//    itself.
//  - Hierarchy is carried by weight, scale, letter-spacing and role contrast: headings are
//    heavy and widely tracked in `onSurface`/`onBackground`, captions are small and tracked
//    in `onSurfaceVariant`, body copy is normal weight with relaxed line height. Pick the
//    concrete type scale per app.
//  - Section dividers are hairline gradient rules fading from `outline` to transparent.
//  - Primary actions invert: filled with `primary`, content in `onPrimary`.
//
// MOTION LANGUAGE (mandatory — animations are part of the product, not decoration):
//  - Each screen keeps a `startAnimation` flag flipped true from LaunchedEffect after a
//    short delay. All entrance animations key off it, so content animates in on every visit.
//  - Entrance pattern: fade (tween, roughly 600-800ms) combined with an upward slide or
//    scale via spring(DampingRatioMediumBouncy, StiffnessLow). Lists stagger their children
//    by index (delayMillis = base + index * a small step).
//  - Ambient loops come from a single rememberInfiniteTransition per composable, exposing
//    named values (particleDrift, scanLine, glowPulse, iconPulse, ringRot, shimmer). They
//    run forever, independent of entrance state.
//  - Press feedback: detectTapGestures(onPress = { pressed = true; tryAwaitRelease(); pressed = false })
//    driving a spring scale slightly below 1.
//  - Numeric values count up with animateIntAsState; progress bars and dividers grow from
//    zero with animateFloatAsState / animateDpAsState.
// The animation values are deliberately left declared in each stripped function body.
// They are the source of truth for the motion design: wire every one of them into the UI
// you build. An implementation that renders the layout but drops these is incomplete.]

@Composable
fun OCEBVAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    //[@AGENT][App theme entry point — the single source of color for the whole app, wrapped
    // around the content in MainActivity. Build a ColorScheme (see Color.kt for how each role
    // is used) and pass it to MaterialTheme along with Typography and Shapes.
    // Whether the app is light, dark, or follows `darkTheme` is a per-app decision; if you
    // support both, define a coherent scheme for each. Avoid dynamic color unless the brand
    // genuinely allows the palette to be replaced by the wallpaper.
    // This function MUST invoke content() inside MaterialTheme — nothing renders otherwise.]
}
