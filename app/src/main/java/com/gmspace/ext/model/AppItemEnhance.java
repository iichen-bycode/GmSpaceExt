package com.gmspace.ext.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.samplekit.bean.AppItem;

import androidx.annotation.NonNull;

public class AppItemEnhance implements Parcelable {
    boolean isExt32 = false;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeByte((byte) (isExt32 ? 1 : 0));
        dest.writeString(appName);
        dest.writeString(packageName);
        dest.writeLong(versionCode);
        dest.writeString(versionName);
        dest.writeString(iconUri);
    }


    public void readFromParcel(Parcel source) {
        this.appName = source.readString();
        this.packageName = source.readString();
        this.versionCode = source.readLong();
        this.versionName = source.readString();
        this.iconUri = source.readString();
        this.isExt32 = source.readByte() == 1;
    }

    protected AppItemEnhance(Parcel in) {
        isExt32 = in.readByte() != 0;
        appName = in.readString();
        packageName = in.readString();
        versionCode = in.readLong();
        versionName = in.readString();
        iconUri = in.readString();
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
}