package com.softartdev.kronos

import kotlinx.datetime.Clock
import platform.Foundation.NSDate

actual val Clock.Companion.Network: NetworkClock
    get() = IosNetworkClock
