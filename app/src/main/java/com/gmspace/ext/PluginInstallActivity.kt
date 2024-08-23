package com.gmspace.ext


import android.os.Bundle
import android.util.Log
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.R



class PluginInstallActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_install)

        GmSpaceObject.initialize(
            application, "fIyzKzyNNBEw1Hnn", "3ppgrZzdkRhunw"
        ) { b, i, s ->
            Log.d("iichen", "初始化有没有成功$b")
            GmSpaceObject.call32BitAppInstallResult(this)
        }
    }
}