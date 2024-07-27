package com.gmspace.ext

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.gmspace.ext.utils.ExtUtils
import com.gmspace.ext.utils.ExtUtils.getAppItem
import com.gmspace.sdk.R
import com.vlite.sdk.VLite
import com.vlite.sdk.utils.GsonUtils
import java.io.File
import java.util.concurrent.Executors


class PluginInstallActivity : AppCompatActivity() {
    val executor = Executors.newSingleThreadExecutor()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_install)

        intent.getStringExtra("mPath")?.apply {
            if (this.isBlank()) {
                finish()
                return
            }

            var packageName:String = ""
            executor.submit {
                val result = ExtUtils.installApplication(
                    this@PluginInstallActivity,
                    this
                )
                packageName = result.data.getString("package_name")?:""
                Log.d(
                    "iichen",
                    ">>>>>>>>>>>>PluginInstall ${result.isSucceed} ${result.message} $packageName"
                )
                val packageInfo: PackageInfo = VLite.get().getPackageInfo(packageName, 0)
                runOnUiThread {
                    val appItem = getAppItem(packageInfo)
                    val uri: Uri = FileProvider.getUriForFile(
                        this@PluginInstallActivity,
                        "gmspace.plugin.provider",  // 对应 FileProvider 配置中的 authorities
                        File(appItem.iconUri) // 文件的实际路径
                    )
                    appItem.iconUri = uri.toString()
                    setResult(0x0002, Intent().putExtra("mAppItem",GsonUtils.toJson(appItem)).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION))
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()
    }
}