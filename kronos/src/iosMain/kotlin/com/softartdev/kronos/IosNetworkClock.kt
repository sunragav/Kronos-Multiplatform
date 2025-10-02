@file:OptIn(ExperimentalForeignApi::class)

package com.softartdev.kronos

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

object IosNetworkClock : NetworkClock {

    override fun sync(pool: String, syncIntervalInMilliSeconds: Long) = Kronos.sync(pool, syncIntervalInMilliSeconds)


    override fun getCurrentNtpTimeMs(): Long? {
        val nsDate: NSDate = Kronos.now() ?: return null
        return nsDate.timeIntervalSince1970.toLong() * 1000
    }
}
