package com.gmspace.ext.utils

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.Log
import com.gmspace.ext.model.AppItemEnhance
import com.gmspace.ext.widget.LauncherAdaptiveIconDrawable
import com.samplekit.bean.AppItem
import com.vlite.sdk.VLite
import com.vlite.sdk.context.HostContext
import com.vlite.sdk.model.ResultParcel
import com.vlite.sdk.utils.BitmapUtils
import java.io.File
import java.io.IOException
import java.util.zip.ZipFile


object ExtUtils {
    fun installApplication(context:Context,path: String) : ResultParcel{
        checkApkEnable(context,path)
        return VLite.get().installPackage(path)
    }

    private fun checkApkEnable(context: Context, uri: String) {
        val minSDKVersion: Int = getMinSdkVersion(uri, context)
        if (minSDKVersion > Build.VERSION.SDK_INT) {
            // 检查apk要求的最低系统版本
            throw RuntimeException("apk最低要求系统api版本 " + minSDKVersion + ", 当前 " + Build.VERSION.SDK_INT)
        }
    }

    private fun getMinSdkVersion(filePath: String, context: Context): Int {
        try {
            val info = context.packageManager.getPackageArchiveInfo(
                filePath, 0
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return info?.applicationInfo?.minSdkVersion?:0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    private fun getSupportedABIs(apkFilePath: String): HashSet<String> {
        val file = File(apkFilePath)
        val supportedABIs: HashSet<String> = HashSet()
        if (file.isDirectory()) {
            val files = file.listFiles { child: File ->
                child.getName().endsWith(".apk")
            }
            for (apkFile in files) {
                addAbiInfo(apkFile.absolutePath, supportedABIs)
            }
        } else {
            addAbiInfo(apkFilePath, supportedABIs)
        }
        return supportedABIs
    }

    private fun addAbiInfo(apkFilePath: String, supportedABIs: HashSet<String>) {
        var zipFile: ZipFile? = null
        try {
            zipFile = ZipFile(apkFilePath)
            val entries = zipFile.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()
                val name = entry.name
                if (name.startsWith("lib/")) {
                    // 获取库文件的架构类型
                    val parts = name.split("/".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                    if (parts.size >= 3) {
                        val abi = parts[1]
                        if (!TextUtils.isEmpty(abi)) {
                            supportedABIs.add(abi)
                        }
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                zipFile?.close()
            } catch (ignored: IOException) {
            }
        }
    }


    fun getAppItem(pkg: PackageInfo) : AppItemEnhance {
        val pm: PackageManager = HostContext.getContext().packageManager
        val launchIntent: ActivityInfo = VLite.get().getLaunchActivityInfoForPackage(pkg.packageName)
        val it = AppItemEnhance()
        it.versionCode = pkg.versionCode.toLong()
        it.versionName = pkg.versionName
        it.packageName = pkg.packageName
        it.appName = pkg.applicationInfo.loadLabel(pm).toString()
        it.iconUri = getIconCacheUri(
            pkg.packageName,
            pkg.versionCode,
            launchIntent.loadIcon(pm),
            launchIntent.name
        )
        it.isExt32 = true
        return it
    }

    private fun getIconCacheUri(packageName: String, versionCode: Int, drawable: Drawable): String {
        return getIconCacheUri(
            packageName,
            versionCode,
            drawable,
            ""
        )
    }

    private fun getIconCacheUri(
        packageName: String,
        versionCode: Int,
        drawable: Drawable,
        imageKey: String
    ): String {
        val iconDir: File = File(HostContext.getContext().externalCacheDir, "icon_cache_v2")
        if (!iconDir.exists()) {
            iconDir.mkdirs()
        }
        val iconFile = File(iconDir, packageName + "_" + versionCode + "_" + imageKey + ".png")
        if (!iconFile.exists()) {
            BitmapUtils.toFile(
                BitmapUtils.toBitmap(
                    convertLauncherDrawable(
                        drawable
                    )
                ), iconFile.absolutePath
            )
        }
        return iconFile.absolutePath
    }

    private fun convertLauncherDrawable(drawable: Drawable): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && drawable is AdaptiveIconDrawable) {
            LauncherAdaptiveIconDrawable(drawable)
        } else {
            drawable
        }
    }
}