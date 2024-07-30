package com.gmspace.ext

import android.app.Application
import android.content.Context
import android.util.Log
import com.gmspace.sdk.GmSpaceConfigContextBuilder
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.GmSpacePackageBuilder
import com.vlite.sdk.BuildConfig
import com.vlite.sdk.LiteConfig
import com.vlite.sdk.VLite
import com.vlite.sdk.logger.AppLogger
import com.vlite.sdk.logger.mars.MarsLogger
import java.util.concurrent.Executors


class App : Application() {
    val executor = Executors.newSingleThreadExecutor()

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

        executor.submit { // 执行后台任务逻辑
            val builder = GmSpaceConfigContextBuilder()
            builder.setGmSpaceForcePictureInPicture(true)
            builder.setGmSpaceUseInternalSdcard(true)
            builder.setGmSpaceIsolatedHost(true)
            builder.setGmSpaceKeepPackageSessionCache(true)
            GmSpaceObject.setGmSpaceConfigurationContext(builder)

            val gmSpacePackageBuilder = GmSpacePackageBuilder()
            gmSpacePackageBuilder.setGmSpaceEnableTraceAnr(true)
            gmSpacePackageBuilder.setGmSpaceAllowCreateShortcut(true)
            gmSpacePackageBuilder.setGmSpaceAllowCreateDynamicShortcut(true)
            gmSpacePackageBuilder.setGmSpaceEnableTraceNativeCrash(true)
            GmSpaceObject.setGmSpacePackageConfiguration(gmSpacePackageBuilder)
        }
    }

    override fun onCreate() {
        Log.d("iichen", ">>>>>>>插件Application onCreate!")
        super.onCreate()
        GmSpaceObject.initialize(
            this, "fIyzKzyNNBEw1Hnn", "3ppgrZzdkRhunw"
        ) { b, i, s -> Log.d("iichen", "初始化有没有成功$b") }
    }
}