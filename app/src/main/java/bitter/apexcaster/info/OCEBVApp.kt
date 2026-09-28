package bitter.apexcaster.info

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import bitter.apexcaster.info.di.dataModule
import bitter.apexcaster.info.di.viewModule

class OCEBVApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = dataModule + viewModule

        startKoin {
            androidLogger()
            androidContext(this@OCEBVApp)
            modules(appModules)
        }
    }
}
