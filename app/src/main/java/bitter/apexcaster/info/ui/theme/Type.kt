package bitter.apexcaster.info.ui.theme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import bitter.apexcaster.info.R
private val provider=GoogleFont.Provider("com.google.android.gms.fonts","com.google.android.gms",R.array.com_google_android_gms_fonts_certs)
private val heading=FontFamily(Font(GoogleFont("Exo 2"),provider,FontWeight.Normal),Font(GoogleFont("Exo 2"),provider,FontWeight.Bold),Font(GoogleFont("Exo 2"),provider,FontWeight.Black))
private val body=FontFamily(Font(GoogleFont("Rajdhani"),provider,FontWeight.Normal),Font(GoogleFont("Rajdhani"),provider,FontWeight.Medium),Font(GoogleFont("Rajdhani"),provider,FontWeight.Bold))
val Typography=Typography(headlineLarge=TextStyle(fontFamily=heading,fontWeight=FontWeight.Black,fontSize=30.sp,letterSpacing=1.sp),headlineMedium=TextStyle(fontFamily=heading,fontWeight=FontWeight.Bold,fontSize=22.sp),titleLarge=TextStyle(fontFamily=heading,fontWeight=FontWeight.Bold,fontSize=18.sp),bodyLarge=TextStyle(fontFamily=body,fontWeight=FontWeight.Medium,fontSize=16.sp),bodyMedium=TextStyle(fontFamily=body,fontWeight=FontWeight.Normal,fontSize=14.sp),labelLarge=TextStyle(fontFamily=body,fontWeight=FontWeight.Bold,fontSize=14.sp,letterSpacing=.8.sp))
