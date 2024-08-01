package com.gmspace.ext

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.R
import com.gmspace.sdk.proxy.GmSpaceUtils
import com.vlite.sdk.context.HostContext

class PluginLaunchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_launch)

        // 需要处理未安装（安装了 但是后续插件被手动卸载再安装那么这里就获取不到之前安装的）
        intent.getStringExtra("mPackageName")?.apply {
            val pm: PackageManager = HostContext.getContext().packageManager
            GmSpaceUtils.getPackageInfo(this,0)?.applicationInfo?.let { applicationInfo ->
                findViewById<TextView>(R.id.iv_app_name).text = applicationInfo.loadLabel(pm).toString()
                findViewById<ImageView>(R.id.iv_app_icon).setImageDrawable(applicationInfo.loadIcon(pm))
            }
        }

        GmSpaceObject.call32BitAppLaunchApplication(this)
    }
}