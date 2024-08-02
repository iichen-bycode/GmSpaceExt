package com.gmspace.ext

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.R
import com.gmspace.sdk.model.AppItemEnhance
import com.vlite.sdk.utils.GsonUtils

class PluginLaunchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_launch)

//        intent.getStringExtra("mAppItem")?.apply {
//            val appItem = GsonUtils.toObject(this, AppItemEnhance::class.java)
//            findViewById<TextView>(R.id.iv_app_name).text = appItem.appName
//            findViewById<ImageView>(R.id.iv_app_icon).setImageBitmap(BitmapFactory.decodeFile(appItem.iconExtUri))
//        }

        GmSpaceObject.call32BitAppLaunchApplication(this)
    }
}