package com.gmspace.ext

import android.os.Bundle
import android.util.Log
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.IGmSpaceInitCallBack
import com.gmspace.sdk.R

class PluginLaunchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_launch)

        GmSpaceObject.call32BitAppLaunchApplication(this)
    }
}