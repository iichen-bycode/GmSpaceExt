package com.gmspace.ext


import android.os.Bundle
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.R



class PluginInstallActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_install)

        GmSpaceObject.call32BitAppInstallResult(this)
    }
}