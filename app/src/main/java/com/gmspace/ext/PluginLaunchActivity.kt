package com.gmspace.ext

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmspace.sdk.R
import com.vlite.sdk.VLite


class PluginLaunchActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_launch)

        intent.getStringExtra("mPackageName")?.apply {
            if (this.isBlank()) {
                finish()
                return
            }
            VLite.get().launchApplication(this)
        }
    }
}