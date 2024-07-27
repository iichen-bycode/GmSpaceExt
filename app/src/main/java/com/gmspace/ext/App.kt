package com.gmspace.ext

import android.app.Application
import android.content.Context
import com.vlite.sdk.BuildConfig
import com.vlite.sdk.LiteConfig
import com.vlite.sdk.VLite
import com.vlite.sdk.logger.AppLogger
import com.vlite.sdk.logger.mars.MarsLogger


class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        // 使用默认配置执行准备工作
        VLite.attachBaseContext(base, BuildConfig.DEBUG)

        VLite.attachBaseContext(
            base, LiteConfig.Builder()
                .setLoggerConfig(
                    AppLogger.Config(BuildConfig.DEBUG)
                        .addLogger(MarsLogger(this))
                )
                .build()
        )
    }


    override fun onCreate() {
        super.onCreate()
        // 初始化SDK
        VLite.initialize(this)
    }
}