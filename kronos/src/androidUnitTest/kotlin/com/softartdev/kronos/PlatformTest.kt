
package com.softartdev.kronos

import kotlinx.datetime.Clock
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class PlatformTest {

    @Ignore // TODO: fix
    @Test
    fun getCurrentNtpTimeMsTest() {
        assertNull(Clock.Network.getCurrentNtpTimeMs())
        assertNotNull(Clock.Network.getCurrentNtpTimeMs())
    }
}
