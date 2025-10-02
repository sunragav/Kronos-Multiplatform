package com.softartdev.kronos

import com.instacart.truetime.time.TrueTime
import com.instacart.truetime.time.TrueTimeImpl
import com.instacart.truetime.time.TrueTimeParameters

object AndroidNetworkClock : NetworkClock {
    lateinit var trueTime: TrueTime

    override fun sync(pool: String, syncIntervalInMilliSeconds: Long) {
        trueTime = TrueTimeImpl(
            params = TrueTimeParameters.Builder()
                .ntpHostPool(arrayListOf(pool))
                .syncIntervalInMillis(syncIntervalInMilliSeconds)
                .buildParams()
        )
        trueTime.sync()
    }

    override fun getCurrentNtpTimeMs(): Long? = try {
        trueTime.nowTrueOnly().time
    } catch (_: Exception) {
        null
    }
}
