package bitter.apexcaster.info.ui.screen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bitter.apexcaster.info.ui.viewmodel.OCEBVWorkoutSessionViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
@Composable fun OCEBVWorkoutSessionScreen(workoutId:Long,type:String,durationMinutes:Int,viewModel:OCEBVWorkoutSessionViewModel=koinViewModel(),onFinish:()->Unit){var seconds by remember{mutableIntStateOf(durationMinutes*60)};var running by remember{mutableStateOf(false)};var confirm by remember{mutableStateOf(false)};LaunchedEffect(running,seconds){if(running&&seconds>0){delay(1000);seconds--}else if(seconds==0)confirm=true};if(confirm)AlertDialog(onDismissRequest={confirm=false},title={Text("Mark workout as completed?")},text={Text("Your progress and history will update.")},confirmButton={TextButton(onClick={viewModel.finishWorkout(workoutId);confirm=false;onFinish()}){Text("CONFIRM")}},dismissButton={TextButton(onClick={confirm=false}){Text("CANCEL")}});Box(Modifier.fillMaxSize(),contentAlignment=Alignment.Center){Column(horizontalAlignment=Alignment.CenterHorizontally){Text(type.uppercase(),style=MaterialTheme.typography.headlineLarge);Text(if(running)"SESSION ACTIVE" else "READY",color=MaterialTheme.colorScheme.secondary);Spacer(Modifier.height(36.dp));Surface(Modifier.size(250.dp),shape=CircleShape,color=MaterialTheme.colorScheme.surface,tonalElevation=8.dp){Box(contentAlignment=Alignment.Center){CircularProgressIndicator(progress={1f-seconds.toFloat()/(durationMinutes*60).coerceAtLeast(1)},modifier=Modifier.fillMaxSize().padding(16.dp),strokeWidth=10.dp);Text("%02d:%02d".format(seconds/60,seconds%60),style=MaterialTheme.typography.headlineLarge)}};Spacer(Modifier.height(36.dp));Row(horizontalArrangement=Arrangement.spacedBy(18.dp)){FilledIconButton(onClick={running=!running},Modifier.size(64.dp)){Icon(if(running)Icons.Default.Pause else Icons.Default.PlayArrow,"Play or pause")};OutlinedIconButton(onClick={confirm=true},Modifier.size(64.dp)){Icon(Icons.Default.Stop,"Finish")}}}}}
