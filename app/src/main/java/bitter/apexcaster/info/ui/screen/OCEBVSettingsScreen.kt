package bitter.apexcaster.info.ui.screen
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
@Composable fun OCEBVSettingsScreen(){val context=LocalContext.current;ApexScreen("Settings","Support and product information."){ApexCard(Modifier.fillMaxWidth()){IconTitle(Icons.Default.Bolt,"Apex Caster","Everyday Momentum activity tracker");HorizontalDivider(Modifier.padding(vertical=16.dp));Text("ABOUT",style=MaterialTheme.typography.labelLarge,color=MaterialTheme.colorScheme.primary);Text("Company: Bitter Lake Retail LLC");Text("Version: 1.0");Text("A practical tracker for guided activity, completed sessions, and weekly progress.",color=MaterialTheme.colorScheme.onSurfaceVariant);Spacer(Modifier.height(16.dp));Button(onClick={context.startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("http://bitterlaakeretail.info")))},Modifier.fillMaxWidth()){Icon(Icons.Default.SupportAgent,null);Spacer(Modifier.width(8.dp));Text("CUSTOMER SUPPORT")}};Spacer(Modifier.height(14.dp));ApexCard(Modifier.fillMaxWidth()){Text("LEGAL & SAFETY",style=MaterialTheme.typography.labelLarge,color=MaterialTheme.colorScheme.primary);Text("Activity information is general and is not medical advice. Stop if you feel pain or dizziness.",color=MaterialTheme.colorScheme.onSurfaceVariant)}}}
@Composable fun MonoSectionLabel(text:String,isVisible:Boolean,delay:Int){Text(text)}
@Composable fun MonoAboutCard(isVisible:Boolean){ApexCard{Text("Apex Caster · 1.0")}}
@Composable fun MonoSettingsItem(icon:ImageVector,title:String,subtitle:String,isVisible:Boolean,delay:Int,onClick:()->Unit){TextButton(onClick=onClick){Icon(icon,null);Text(title)}}
