package bitter.apexcaster.info.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val ApexColors = darkColorScheme(primary=ApexOrange,onPrimary=Color.White,primaryContainer=ApexOrangeDark,onPrimaryContainer=Color.White,secondary=ApexTeal,onSecondary=ApexBackground,background=ApexBackground,onBackground=ApexText,surface=ApexSurface,onSurface=ApexText,surfaceVariant=ApexSurfaceVariant,onSurfaceVariant=ApexMuted,outline=ApexBorder,error=Color(0xFFFF6B6B))
@Composable fun OCEBVAppTheme(darkTheme:Boolean=true,content:@Composable()->Unit){ MaterialTheme(colorScheme=ApexColors,typography=Typography,shapes=Shapes,content=content) }
