package bitter.apexcaster.info.ui.screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import bitter.apexcaster.info.ui.viewmodel.OCEBVOCEBVOnboardingVM
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
data class OnboardingPage(val title:String,val description:String,val icon:ImageVector,val stat:String)
@Composable fun OCEBVOnboardingScreen(viewModel:OCEBVOCEBVOnboardingVM=koinViewModel(),onFinish:()->Unit){val pages=listOf(OnboardingPage("Find your move","Choose practical cardio, strength, mobility, and recovery sessions.",Icons.Default.DirectionsRun,"10+ GUIDED ROUTINES"),OnboardingPage("Train with focus","Follow clear session details and use the timer to stay present.",Icons.Default.Timer,"10–45 MINUTES"),OnboardingPage("Build momentum","Save completed activity and watch your consistency grow.",Icons.Default.TrendingUp,"7-DAY PROGRESS"));val pager=rememberPagerState(pageCount={pages.size});val scope=rememberCoroutineScope();Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)){HorizontalPager(pager,Modifier.fillMaxSize()){page->val item=pages[page];Column(Modifier.fillMaxSize().padding(30.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){Surface(shape=CircleShape,color=MaterialTheme.colorScheme.primary,modifier=Modifier.size(150.dp)){Icon(item.icon,null,Modifier.padding(38.dp),tint=MaterialTheme.colorScheme.onPrimary)};Spacer(Modifier.height(34.dp));Text(item.stat,color=MaterialTheme.colorScheme.secondary,style=MaterialTheme.typography.labelLarge);Text(item.title,style=MaterialTheme.typography.headlineLarge);Text(item.description,color=MaterialTheme.colorScheme.onSurfaceVariant,style=MaterialTheme.typography.bodyLarge)}};Column(Modifier.align(Alignment.BottomCenter).padding(24.dp).navigationBarsPadding(),horizontalAlignment=Alignment.CenterHorizontally){Row{repeat(pages.size){i->Box(Modifier.padding(4.dp).size(if(i==pager.currentPage)24.dp else 8.dp,8.dp).background(if(i==pager.currentPage)MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,CircleShape))}};Spacer(Modifier.height(12.dp));Button(onClick={if(pager.currentPage==pages.lastIndex){viewModel.completeOnboarding();onFinish()}else scope.launch{pager.animateScrollToPage(pager.currentPage+1)}},modifier=Modifier.fillMaxWidth()){Text(if(pager.currentPage==pages.lastIndex)"GET STARTED" else "NEXT")}}}}
