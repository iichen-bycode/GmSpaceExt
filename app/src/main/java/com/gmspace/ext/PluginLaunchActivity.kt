package com.gmspace.ext

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.R
import com.vlite.sdk.VLite
import java.util.concurrent.Executors


class PluginLaunchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_launch)

        GmSpaceObject.call32BitAppLaunchApplication(this,executor)
    }
}