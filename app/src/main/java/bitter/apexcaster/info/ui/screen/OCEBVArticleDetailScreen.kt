package bitter.apexcaster.info.ui.screen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import bitter.apexcaster.info.ui.viewmodel.OCEBVArticleDetailViewModel
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
@Composable fun OCEBVArticleDetailScreen(articleId:Long,viewModel:OCEBVArticleDetailViewModel=koinViewModel(),onBackClick:()->Unit){val item by viewModel.article.collectAsState();var dialog by remember{mutableStateOf(false)};if(dialog)AlertDialog(onDismissRequest={dialog=false},title={Text("Start this workout?")},text={Text("Choose an appropriate level and stop if you feel pain or dizziness.")},confirmButton={TextButton(onClick={dialog=false}){Text("START")}},dismissButton={TextButton(onClick={dialog=false}){Text("CANCEL")}});Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())){Box{AsyncImage(model=item?.imageUrl,contentDescription=item?.title,modifier=Modifier.fillMaxWidth().height(260.dp),contentScale=ContentScale.Crop);IconButton(onClick=onBackClick,modifier=Modifier.statusBarsPadding()){Icon(Icons.Default.ArrowBack,"Back")}};Column(Modifier.padding(18.dp)){Text("${durationFor(articleId)} MIN · ${exerciseCount(articleId)} EXERCISES",color=MaterialTheme.colorScheme.primary,style=MaterialTheme.typography.labelLarge);Text(item?.title?:"Loading workout",style=MaterialTheme.typography.headlineLarge);Text("${categoryType(item?.category?:"Health")} · ${articleLevel(articleId)}",color=MaterialTheme.colorScheme.secondary);Spacer(Modifier.height(18.dp));listOf("Warm-up flow","Main movement","Controlled tempo set","Active recovery","Cooldown breathing").take(exerciseCount(articleId)).forEachIndexed{i,name->ApexCard(Modifier.fillMaxWidth().padding(vertical=5.dp)){Text("${i+1}. $name",style=MaterialTheme.typography.titleLarge);Text(if(i%2==0)"3 sets × 10 reps · 30 sec rest" else "45 sec · 20 sec rest",color=MaterialTheme.colorScheme.onSurfaceVariant)}};Spacer(Modifier.height(12.dp));Button(onClick={dialog=true},Modifier.fillMaxWidth()){Text("START WORKOUT")};Spacer(Modifier.height(30.dp))}}}
