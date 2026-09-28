package bitter.apexcaster.info.ui.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import bitter.apexcaster.info.data.local.entity.*
import bitter.apexcaster.info.ui.viewmodel.OCEBVHomeViewModel
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
@Composable fun OCEBVHomeScreen(viewModel:OCEBVHomeViewModel=koinViewModel(),onArticleClick:(Long)->Unit,onStartWorkoutClick:(OCEBVWorkoutEntity)->Unit){val articles by viewModel.articles.collectAsState();val planned by viewModel.plannedWorkouts.collectAsState();var type by remember{mutableStateOf("All")};var level by remember{mutableStateOf("All")};val types=listOf("All","Cardio","Strength","Mobility","Recovery");val levels=listOf("All","Beginner","Intermediate","Advanced");val filtered=articles.filter{(type=="All"||categoryType(it.category)==type)&&(level=="All"||articleLevel(it.id)==level)};ApexScreen("Apex Caster","Small sessions. Stronger routines."){GradientGoalCard("3 / 5","workouts toward this week's goal");Spacer(Modifier.height(16.dp));Text("ACTIVITY",style=MaterialTheme.typography.labelLarge);LazyRow(horizontalArrangement=Arrangement.spacedBy(8.dp)){items(types){FilterChip(selected=type==it,onClick={type=it},label={Text(it)})}};LazyRow(horizontalArrangement=Arrangement.spacedBy(8.dp)){items(levels){FilterChip(selected=level==it,onClick={level=it},label={Text(it)})}};LazyColumn(verticalArrangement=Arrangement.spacedBy(14.dp)){if(planned.isNotEmpty()){item{Text("READY TO START",style=MaterialTheme.typography.labelLarge)};items(planned){w->ApexCard(Modifier.fillMaxWidth().clickable{onStartWorkoutClick(w)}){Text(w.type,style=MaterialTheme.typography.titleLarge);Text("${w.durationMinutes} min · ${w.environment}",color=MaterialTheme.colorScheme.onSurfaceVariant)}}};items(filtered){item->WorkoutLibraryCard(item,onArticleClick)}}}}
@Composable private fun WorkoutLibraryCard(item:OCEBVArticleEntity,onClick:(Long)->Unit){Card(Modifier.fillMaxWidth().clickable{onClick(item.id)},shape=RoundedCornerShape(18.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.surface)){AsyncImage(model=item.imageUrl,contentDescription=item.title,modifier=Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(topStart=18.dp,topEnd=18.dp)),contentScale=ContentScale.Crop);Row(Modifier.fillMaxWidth()){Box(Modifier.width(6.dp).height(102.dp).background(MaterialTheme.colorScheme.primary));Column(Modifier.padding(14.dp)){Text("${durationFor(item.id)} MIN",color=MaterialTheme.colorScheme.primary,style=MaterialTheme.typography.headlineMedium);Text(item.title,style=MaterialTheme.typography.titleLarge);Text("${categoryType(item.category)} · ${articleLevel(item.id)} · ${exerciseCount(item.id)} exercises",color=MaterialTheme.colorScheme.onSurfaceVariant)}}}}
fun categoryType(category:String)=when(category){"Running","Swimming","Cycling","Cardio"->"Cardio";"Strength"->"Strength";"Flexibility"->"Mobility";else->"Recovery"}
fun articleLevel(id:Long)=listOf("Beginner","Intermediate","Advanced")[(id.toInt().coerceAtLeast(1)-1)%3]
fun durationFor(id:Long)=listOf(18,22,30,16,25,35,12,20,24,15)[(id.toInt().coerceAtLeast(1)-1)%10]
fun exerciseCount(id:Long)=4+(id.toInt().coerceAtLeast(1)%5)
@Composable fun MonoDeleteDialog(onConfirm:()->Unit,onDismiss:()->Unit){AlertDialog(onDismissRequest=onDismiss,title={Text("Remove workout?")},text={Text("This action cannot be undone.")},confirmButton={TextButton(onClick=onConfirm){Text("REMOVE")}},dismissButton={TextButton(onClick=onDismiss){Text("CANCEL")}})}
