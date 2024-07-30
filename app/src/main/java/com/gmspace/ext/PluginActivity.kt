package com.gmspace.ext

import android.content.ComponentName
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gmspace.ext.utils.ExtUtils
import com.gmspace.sdk.R
import com.vlite.sdk.VLite
import com.vlite.sdk.model.ConfigurationContext
import com.vlite.sdk.model.PackageConfiguration
import java.util.concurrent.Executors


class PluginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_plugin)
    }


//    fun installApp(view: View) {
//        if(!VLite.get().isPackageInstalled("com.yzlckb.batu")) {
//            val result = ExtUtils.installApplication(
//                this,
//                "/data/app/~~RihlveUhVC30XL4BvK239A==/com.yzlckb.batu-Uxs_KYGgvl3YnjaU7zSSxA==/base.apk"
//            )
//            Log.d("iichen", ">>>>>>>>>>>> ${result.isSucceed} ${result.message} ${result.data.getString("package_name")}")
//        }
//    }
//
//    fun startApp(view: View) {
//        VLite.get().launchApplication("com.yzlckb.batu")
//    }
//
//    fun unInstallApp(view: View) {
//        VLite.get().uninstallPackage("com.yzlckb.batu")
//    }
}