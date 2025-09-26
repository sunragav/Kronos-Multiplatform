package com.softartdev.kronos

import android.content.Context
import com.lyft.kronos.AndroidClockFactory
import com.lyft.kronos.KronosClock
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val NTP_HOST_CLOUD_FLARE = "time.cloudflare.com"
private const val NTP_HOST_GOOGLE = "time.google.com"
object AndroidNetworkClock : NetworkClock {
    lateinit var kronosClock: KronosClock

    fun sync(applicationContext: Context) {
        initClock(applicationContext)
        kronosClock.syncInBackground()
    }

    fun blockingSync(applicationContext: Context): Boolean {
        initClock(applicationContext)
        return kronosClock.sync()
    }

    suspend fun awaitSync(applicationContext: Context): Boolean = suspendCoroutine { continuation ->
        initClock(applicationContext)
        try {
            val result = kronosClock.sync()
            continuation.resume(result)
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    private fun initClock(applicationContext: Context) {
        if (::kronosClock.isInitialized) return
        kronosClock = AndroidClockFactory.createKronosClock(applicationContext, ntpHosts = listOf(NTP_HOST_CLOUD_FLARE, NTP_HOST_GOOGLE))
    }

    override fun getCurrentNtpTimeMs(): Long? = when {
        ::kronosClock.isInitialized -> kronosClock.getCurrentTimeMs()
        else -> null
    }
}
