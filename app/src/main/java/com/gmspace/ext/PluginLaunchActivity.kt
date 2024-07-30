package com.gmspace.ext

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.R
import com.gmspace.sdk.proxy.GmSpaceUtils
import com.vlite.sdk.VLite
import com.vlite.sdk.context.HostContext
import java.util.concurrent.Executors


class PluginLaunchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_launch)

        intent.getStringExtra("mPackageName")?.apply {
            val pm: PackageManager = HostContext.getContext().packageManager
            GmSpaceUtils.getPackageInfo(this,0).applicationInfo.let { applicationInfo ->
                findViewById<TextView>(R.id.iv_app_name).text = applicationInfo.loadLabel(pm).toString()
                findViewById<ImageView>(R.id.iv_app_icon).setImageDrawable(applicationInfo.loadIcon(pm))
            }
        }

        GmSpaceObject.call32BitAppLaunchApplication(this,executor)
    }
}