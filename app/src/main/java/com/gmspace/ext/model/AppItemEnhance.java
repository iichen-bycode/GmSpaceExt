package com.gmspace.ext.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.samplekit.bean.AppItem;

import androidx.annotation.NonNull;

public class AppItemEnhance implements Parcelable {
    boolean isExt32 = false;
    boolean isOverride = false;

    protected AppItemEnhance(Parcel in) {
        isExt32 = in.readByte() != 0;
        isOverride = in.readByte() != 0;
        appName = in.readString();
        packageName = in.readString();
        versionCode = in.readLong();
        versionName = in.readString();
        iconUri = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isExt32 ? 1 : 0));
        dest.writeByte((byte) (isOverride ? 1 : 0));
        dest.writeString(appName);
        dest.writeString(packageName);
        dest.writeLong(versionCode);
        dest.writeString(versionName);
        dest.writeString(iconUri);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppItemEnhance> CREATOR = new Creator<AppItemEnhance>() {
        @Override
        public AppItemEnhance createFromParcel(Parcel in) {
            return new AppItemEnhance(in);
        }

        @Override
        public AppItemEnhance[] newArray(int size) {
            return new AppItemEnhance[size];
        }
    };

    public boolean isOverride() {
        return isOverride;
    }

    public void setOverride(boolean override) {
        isOverride = override;
    }

    public AppItemEnhance() {
    }

    public boolean isExt32() {
        return isExt32;
    }

    public void setExt32(boolean ext32) {
        isExt32 = ext32;
    }

    private String appName;
    private String packageName;
    private long versionCode;
    private String versionName;
    private String iconUri;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public long getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}