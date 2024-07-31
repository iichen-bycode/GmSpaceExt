package com.gmspace.ext

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.R
import com.vlite.sdk.VLite


class PluginUnInstallActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_uninstall)

        GmSpaceObject.call32BitAppUnInstallResult(this)
    }
}