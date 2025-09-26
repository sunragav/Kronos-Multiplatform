package com.softartdev.kronos.sample

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.softartdev.kronos.JvmNetworkClock
import java.awt.Desktop
import java.net.URI

internal actual fun openUrl(url: String?) {
    val uri = url?.let { URI.create(it) } ?: return
    Desktop.getDesktop().browse(uri)
}

internal actual fun clickSync() = JvmNetworkClock.sync()

@Preview
@Composable
fun AppPreview() = App()
