package com.gmspace.ext

import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors

open class BaseActivity : AppCompatActivity() {
    val executor = Executors.newSingleThreadExecutor()

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()
    }
}