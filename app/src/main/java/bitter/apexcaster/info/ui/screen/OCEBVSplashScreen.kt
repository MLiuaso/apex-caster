package bitter.apexcaster.info.ui.screen

import bitter.apexcaster.info.R
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bitter.apexcaster.info.ui.viewmodel.OCEBVOCEBVSplashVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun OCEBVSplashScreen(
    viewModel: OCEBVOCEBVSplashVM = koinViewModel(),
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val loading by viewModel.isLoading.collectAsState();
    val done by viewModel.isOnboardingCompleted.collectAsState();
    val pulse = rememberInfiniteTransition(label = "pulse").animateFloat(
        .92f,
        1.08f,
        infiniteRepeatable(tween(700), RepeatMode.Reverse),
        label = "scale"
    ).value;LaunchedEffect(loading) {
        if (!loading) {
            if (done) onNavigateToHome() else onNavigateToOnboarding()
        }
    };Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            ), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                Modifier
                    .size(112.dp)
                    .scale(pulse),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Image(
                    painter = painterResource(R.drawable.icon),
                    "Apex Caster",
                    Modifier.padding(26.dp),
                    )
            };Spacer(Modifier.height(24.dp));Text(
            "APEX CASTER",
            style = MaterialTheme.typography.headlineLarge,
            letterSpacing = 4.sp
        );Text(
            "EVERYDAY MOMENTUM",
            color = MaterialTheme.colorScheme.secondary,
            letterSpacing = 2.sp
        )
        }
    }
}

@Composable
fun SportMedallion(shimmer: Float, glowPulse: Float) {
    Icon(Icons.Default.Bolt, "Activity", tint = MaterialTheme.colorScheme.primary)
}

@Composable
fun LoadingDots(alpha: Float) {
    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
}

@Composable
fun SpeedParticles(phase: Float, modifier: Modifier = Modifier) {
    Spacer(modifier)
}

@Composable
fun SportTagStrip(modifier: Modifier = Modifier) {
    Text(
        "MOVE  •  BUILD  •  RECOVER",
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.labelLarge
    )
}
