package com.softartdev.kronos

import kotlinx.datetime.Clock

actual val Clock.Companion.Network: NetworkClock
    get() = JvmNetworkClock
