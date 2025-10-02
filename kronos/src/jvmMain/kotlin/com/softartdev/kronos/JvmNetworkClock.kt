package com.softartdev.kronos

import com.lyft.kronos.KronosClock

object JvmNetworkClock : NetworkClock {
    lateinit var kronosClock: KronosClock

    override fun getCurrentNtpTimeMs(): Long? = when {
        ::kronosClock.isInitialized -> kronosClock.getCurrentTimeMs()
        else -> null
    }

    override fun sync(pool: String, syncIntervalInMilliSeconds: Long) {
        kronosClock = JvmClockFactory.createKronosClock(
            ntpHosts = listOf(pool),
            minWaitTimeBetweenSyncMs = syncIntervalInMilliSeconds
        )
        kronosClock.sync()
    }
}
