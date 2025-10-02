import androidx.compose.ui.window.ComposeUIViewController
import com.softartdev.kronos.Network
import com.softartdev.kronos.sample.App
import com.softartdev.kronos.syncWithCloudFlare
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.datetime.Clock
import platform.UIKit.UIViewController

fun appInit() {
    Napier.base(DebugAntilog())
    Clock.Network.syncWithCloudFlare()
}

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
