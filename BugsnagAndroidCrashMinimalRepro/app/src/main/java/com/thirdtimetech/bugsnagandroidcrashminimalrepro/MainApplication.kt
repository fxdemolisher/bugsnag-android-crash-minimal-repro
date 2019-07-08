package com.thirdtimetech.bugsnagandroidcrashminimalrepro

import android.app.Application
import com.bugsnag.android.Bugsnag
import com.bugsnag.android.Configuration

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // NOTE: this was tested with a development key for our org.
        //       to run the minimal repro you will need to use your own key.
        val config = Configuration("API_KEY_GOES_HERE")
        config.maxBreadcrumbs = 100
        config.releaseStage = "development"
        config.detectAnrs = true
        config.detectNdkCrashes = true
        config.autoCaptureSessions = false
        Bugsnag.init(this, config)
    }
}