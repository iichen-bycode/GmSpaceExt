### 1. 安装失败或者判断是否32位后执行
```agsl
    // 启动安装应用
    private void test(String absolutePath) {
        if(isAppInstalled("com.gmspace.sdk")) {
            Intent intent = new Intent();
            intent.putExtra("mPath", absolutePath);
            intent.setComponent(new ComponentName("com.gmspace.sdk", "com.gmspace.ext.PluginInstallActivity"));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivityForResult(intent, 0x0001);
        } else {
            Toast.makeText(this,"请先安装32位插件",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    
    
      @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x0001 && resultCode == 0x0002 && data != null) {
            String json = data.getStringExtra("mAppItem");
            AppItemEnhance mAppItem = GsonUtils.toObject(json,AppItemEnhance.class);
            if (mAppItem != null) {
                final Fragment fragment = getSupportFragmentManager().findFragmentById(binding.contentFragment.getId());
                if (fragment instanceof LauncherFragment) {
                    ((LauncherFragment) fragment).notify32AppInstall(mAppItem);
                }
            }
        }
    }
    
    
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
```

### 2. 启动应用
```agsl
        if(item.isExt32()) {
            Intent intent = new Intent();
            intent.putExtra("mPackageName", item.getPackageName());
            intent.setComponent(new ComponentName("com.gmspace.sdk", "com.gmspace.ext.PluginLaunchActivity"));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            startActivity(LaunchAppActivity.getIntent(item));
        }

```

### 2. 卸载应用
```agsl


```

