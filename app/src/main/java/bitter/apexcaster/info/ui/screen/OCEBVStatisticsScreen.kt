package bitter.apexcaster.info.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import bitter.apexcaster.info.ui.viewmodel.OCEBVStatisticsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OCEBVStatisticsScreen(viewModel: OCEBVStatisticsViewModel = koinViewModel()) {
    val count by viewModel.totalWorkouts.collectAsState()
    val minutes by viewModel.totalDurationMinutes.collectAsState()
    val byType by viewModel.workoutsByType.collectAsState()
    val favorite = byType.maxByOrNull { it.value }?.key ?: "—"

    ApexScreen(
        title = "Progress",
        subtitle = "Your activity, one useful signal at a time."
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                StatTile(count.toString(), "TOTAL WORKOUTS")
            }

            item {
                StatTile("%.1f".format(minutes / 60f), "TOTAL HOURS")
            }

            item {
                StatTile(favorite, "FAVORITE TYPE")
            }

            item {
                StatTile("${count.coerceAtMost(7)} / 7", "WEEKLY ACTIVITY")
            }

            item(span = { GridItemSpan(2) }) {
                ApexCard(Modifier.fillMaxWidth()) {
                    Text(
                        text = "THIS WEEK",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(Modifier.height(18.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        listOf(.25f, .5f, .35f, .8f, .55f, .2f, .65f).forEachIndexed { i, v ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .fillMaxHeight(v)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                )
                                Text(
                                    text = listOf("M", "T", "W", "T", "F", "S", "S")[i],
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            } // Тут була проблема: наступний item був випадково вкладений сюди

            item(span = { GridItemSpan(2) }) {
                ApexCard(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Keep the chain moving",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "A short session still counts. Choose the routine that fits the energy you have today.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun StatTile(value: String, label: String) {
    ApexCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(126.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
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
    StatTile(value.toString(), title)
}

@Composable
fun MonoBreakdownCard(workoutsByType: Map<String, Int>, isVisible: Boolean) {
    ApexCard {
        workoutsByType.forEach { (k, v) ->
            Text("$k · $v")
        }
    }
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
    Text("$type · $count")
}