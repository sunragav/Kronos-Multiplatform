package com.softartdev.kronos

import com.instacart.truetime.time.TrueTimeImpl
import com.instacart.truetime.time.TrueTimeParameters

private const val SYNC_INTERNAL_IN_MILLIS = 60_000L
private const val NTP_HOST_CLOUD_FLARE = "time.cloudflare.com"
private const val NTP_HOST_GOOGLE = "time.google.com"

object AndroidNetworkClock : NetworkClock {
    private val trueTime = TrueTimeImpl(
        params = TrueTimeParameters.Builder()
            .ntpHostPool(arrayListOf(
               NTP_HOST_CLOUD_FLARE,
                NTP_HOST_GOOGLE
            ))
            .syncIntervalInMillis(SYNC_INTERNAL_IN_MILLIS)
            .buildParams()
    )

    fun sync() {
        trueTime.sync()
    }

    override fun getCurrentNtpTimeMs(): Long = trueTime.now().time
}
