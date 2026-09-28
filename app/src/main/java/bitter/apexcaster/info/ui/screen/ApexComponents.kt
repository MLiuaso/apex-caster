package bitter.apexcaster.info.ui.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
@Composable fun ApexScreen(title:String,subtitle:String,content:@Composable ColumnScope.()->Unit){Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).windowInsetsPadding(WindowInsets.statusBars).padding(horizontal=18.dp)){Spacer(Modifier.height(18.dp));Text(title.uppercase(),style=MaterialTheme.typography.headlineLarge);Text(subtitle,color=MaterialTheme.colorScheme.onSurfaceVariant);Spacer(Modifier.height(18.dp));content()}}
@Composable fun ApexCard(modifier:Modifier=Modifier,content:@Composable ColumnScope.()->Unit){Card(modifier,shape=RoundedCornerShape(18.dp),colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.surface)){Column(Modifier.padding(16.dp),content=content)}}
@Composable fun GradientGoalCard(value:String,label:String){Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary,MaterialTheme.colorScheme.primaryContainer))).padding(20.dp)){Text(value,style=MaterialTheme.typography.headlineLarge,color=MaterialTheme.colorScheme.onPrimary);Text(label,color=MaterialTheme.colorScheme.onPrimary);Spacer(Modifier.height(12.dp));LinearProgressIndicator(progress={.6f},modifier=Modifier.fillMaxWidth(),color=MaterialTheme.colorScheme.secondary)}}
@Composable fun IconTitle(icon:ImageVector,title:String,subtitle:String){Row(verticalAlignment=Alignment.CenterVertically){Surface(shape=RoundedCornerShape(14.dp),color=MaterialTheme.colorScheme.surfaceVariant){Icon(icon,null,Modifier.padding(12.dp),tint=MaterialTheme.colorScheme.primary)};Spacer(Modifier.width(12.dp));Column{Text(title,style=MaterialTheme.typography.titleLarge);Text(subtitle,color=MaterialTheme.colorScheme.onSurfaceVariant)}}}
