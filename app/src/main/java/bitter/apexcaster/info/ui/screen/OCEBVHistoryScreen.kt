package bitter.apexcaster.info.ui.screen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bitter.apexcaster.info.data.local.entity.OCEBVWorkoutEntity
import bitter.apexcaster.info.ui.viewmodel.OCEBVHistoryViewModel
import java.text.SimpleDateFormat
import java.util.*
import org.koin.androidx.compose.koinViewModel
@Composable fun OCEBVHistoryScreen(viewModel:OCEBVHistoryViewModel=koinViewModel()){val workouts by viewModel.workouts.collectAsState();var deleting by remember{mutableStateOf<OCEBVWorkoutEntity?>(null)};if(deleting!=null)MonoDeleteDialog({viewModel.deleteWorkout(deleting!!);deleting=null},{deleting=null});ApexScreen("History","Every completed session builds momentum."){GradientGoalCard(workouts.size.toString(),"total completed workouts · ${workouts.take(7).size}-day momentum");Spacer(Modifier.height(16.dp));if(workouts.isEmpty())ApexCard(Modifier.fillMaxWidth()){Text("Complete your first workout!",style=MaterialTheme.typography.titleLarge);Text("Finished sessions will appear here with their activity details.",color=MaterialTheme.colorScheme.onSurfaceVariant)}else LazyColumn(verticalArrangement=Arrangement.spacedBy(10.dp)){items(workouts){w->ApexCard(Modifier.fillMaxWidth()){Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){Column{Text(w.type,style=MaterialTheme.typography.titleLarge);Text(SimpleDateFormat("MMM d, yyyy · h:mm a",Locale.US).format(Date(w.dateInMillis)),color=MaterialTheme.colorScheme.onSurfaceVariant);Text("${w.durationMinutes} min · ${w.environment}",color=MaterialTheme.colorScheme.secondary)};TextButton(onClick={deleting=w}){Text("REMOVE")}}}}}}}
@Composable fun MonoHistoryWorkoutCard(workout:OCEBVWorkoutEntity,onDeleteClick:()->Unit,index:Int,isVisible:Boolean){ApexCard{Text(workout.type);TextButton(onClick=onDeleteClick){Text("REMOVE")}}}
