package bitter.apexcaster.info

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import bitter.apexcaster.info.ui.approot.AppRoot
import bitter.apexcaster.info.ui.theme.OCEBVAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // OCEBVAppTheme is the only source of color for the app — every composable below
            // reads MaterialTheme.colorScheme rather than hardcoding values.
            OCEBVAppTheme {
                AppRoot()
            }
        }
    }
}
