package com.gmspace.ext

import android.content.ComponentName
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gmspace.ext.utils.ExtUtils
import com.gmspace.sdk.R
import com.vlite.sdk.VLite
import com.vlite.sdk.model.ConfigurationContext
import com.vlite.sdk.model.PackageConfiguration
import java.util.concurrent.Executors


class PluginActivity : AppCompatActivity() {
    val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_plugin)

        executor.submit { // 执行后台任务逻辑
            val configuration = ConfigurationContext.Builder()
                .setUseInternalSdcard(true)
                .setIsolatedHost(true)
                .setKeepPackageSessionCache(true)
                .setForcePictureInPicture(true)
                .build()
            VLite.get().setConfigurationContext(configuration, true)


            val packageConfiguration = PackageConfiguration.Builder() // 是否允许创建桌面快捷方式
                .setEnableTraceAnr(true)
                .setAllowCreateShortcut(true)
                .setAllowCreateDynamicShortcut(true)
                .setEnableTraceNativeCrash(true)
                .build()
            VLite.get().setPackageConfiguration(packageConfiguration, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()
    }

    fun installApp(view: View) {
        if(!VLite.get().isPackageInstalled("com.yzlckb.batu")) {
            val result = ExtUtils.installApplication(
                this,
                "/data/app/~~RihlveUhVC30XL4BvK239A==/com.yzlckb.batu-Uxs_KYGgvl3YnjaU7zSSxA==/base.apk"
            )
            Log.d("iichen", ">>>>>>>>>>>> ${result.isSucceed} ${result.message} ${result.data.getString("package_name")}")
        }
    }

    fun startApp(view: View) {
        VLite.get().launchApplication("com.yzlckb.batu")
    }

    fun unInstallApp(view: View) {
        VLite.get().uninstallPackage("com.yzlckb.batu")
    }
}