package com.softartdev.kronos

import kotlinx.datetime.Clock

private const val SYNC_INTERNAL_IN_MILLIS = 60_000L
private const val NTP_HOST_CLOUD_FLARE = "time.cloudflare.com"

expect val Clock.Companion.Network: NetworkClock

fun NetworkClock.syncWithCloudFlare(timeIntervalInMilliSeconds: Long = SYNC_INTERNAL_IN_MILLIS) =
    sync(NTP_HOST_CLOUD_FLARE, timeIntervalInMilliSeconds)
