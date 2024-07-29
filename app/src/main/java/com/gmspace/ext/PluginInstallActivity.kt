package com.gmspace.ext

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.gmspace.ext.utils.ExtUtils
import com.gmspace.ext.utils.ExtUtils.getAppItem
import com.gmspace.sdk.GmSpaceObject
import com.gmspace.sdk.R
import com.vlite.sdk.VLite
import com.vlite.sdk.context.HostContext
import com.vlite.sdk.reflect.android.content.Ref_Intent.putExtra
import com.vlite.sdk.utils.GsonUtils
import java.io.File
import java.util.concurrent.Executors


class PluginInstallActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_install)

        GmSpaceObject.call32BitAppInstallResult(this,executor)
    }
}