package com.gmspace.ext

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gmspace.sdk.R
import com.vlite.sdk.VLite


class PluginUnInstallActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_uninstall)

        intent.getStringExtra("mPackageName")?.apply {
            if (this.isBlank()) {
                finish()
                return
            }
            executor.submit {
                val result = VLite.get().uninstallPackage(this)
                Log.d("iichen",">>>>>>>>>>>>>>>>>>卸载结果 $result")
                finish()
            }
        }
    }
}