### 1. 安装应用
```md
        if(mProgressDialog == null) {
            final View view = LayoutInflater.from(this).inflate(R.layout.dialog_material_loading, null);
            TextView mMessageView = view.findViewById(android.R.id.message);
                        mMessageView.setText("安装中");
            mProgressDialog  = new AlertDialog.Builder(this, R.style.Theme_App_Dialog)
                                .setView(view)
                                .setCancelable(false).create();
                    }
                            if (src == null || !src.exists()) {
            setSubtitle("文件不存在 " + (src == null ? null : src.getAbsolutePath()));
                    return;
        }

        mProgressDialog.show();
        GmSpaceObject.installCompatiblePackage(this,src.getAbsolutePath(),null);
```
> 安装回调（在统一回调内处理）
```md
    // 当前宿主支持的游戏可以直接通过    GmSpaceResultParcel resultParcel的isSucceed判断 32位不支持
    GmSpaceObject.registerGmSpaceCompatibleEventListener(new OnGmSpaceReceivedEventListener() {
        @Override
        public void onReceivedEvent(int type, Bundle extras) {
            if(mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            if (GmSpaceEvent.TYPE_PACKAGE_INSTALLED == type) {
                // 有应用安装 返回安装的应用信息 （KEY_PACKAGE_COMPATIBLE_STATUS 可以获取安装是否成功）
                AppItemEnhance appItemEnhance = extras.getParcelable(GmSpaceEvent.KEY_PACKAGE_COMPATIBLE_INFO);
                // appItemEnhance.isOverride() 是否重复安装
                // appItemEnhance.isExt32() 是否32位

                // GmSpaceEvent.KEY_PACKAGE_COMPATIBLE_INSTALL_RESULT 获取返回结果GmSpaceResultParcel 具体原因等
            } else if (GmSpaceEvent.TYPE_PACKAGE_UNINSTALLED == type) {
                // 有应用卸载 （KEY_PACKAGE_COMPATIBLE_STATUS 可以获取卸载是否成功）
                final String packageName = extras.getString(GmSpaceEvent.KEY_PACKAGE_NAME);
            } else if (GmSpaceEvent.TYPE_COMPONENT_SETTING_CHANGE == type){
                // 组件状态变化 返回应用信息
                AppItemEnhance appItemEnhance = extras.getParcelable(GmSpaceEvent.KEY_PACKAGE_COMPATIBLE_INFO);
                String message = extras.getParcelable(GmSpaceEvent.KEY_PACKAGE_COMPATIBLE_MESSAGE);
            } else if(GmSpaceEvent.TYPE_PACKAGE_EXT_NOT_INSTALL == type) {
                // 调用安装、卸载、启动应用若指定32插件应用未安装 会提示，可以在这里 GmSpaceObject.getInstalledCompatiblePackages() 以刷新删除安装的32位应用列表显示
            } else if(GmSpaceEvent.TYPE_PACKAGE_EXT_APPLICATION_NOT_INSTALL) {
                // 32位插件里的应用已经卸载 去刷新当前列表移除当前item
            }
    });
```

### 2. 应用卸载
```md
     public void asyncUninstallApp(Context context, AppItemEnhance appItemEnhance) {
        if(mProgressDialog == null) {
            final View view = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_material_loading, null);
            TextView mMessageView = view.findViewById(android.R.id.message);
            mMessageView.setText("卸载中");
            mProgressDialog  = new AlertDialog.Builder(requireActivity(), R.style.Theme_App_Dialog)
                    .setView(view)
                    .setCancelable(false).create();
        }
        mProgressDialog.show();
        GmSpaceObject.uninstallCompatiblePackage(requireActivity(), appItemEnhance);
    }
```

### 3. 应用启动
```md
    private void asyncLaunchApp(AppItemEnhance item) {
        GlideUtils.loadFadeSkipCache(binding.ivAppIcon, item.getIconUri());
        binding.tvAppName.setText(item.getAppName());
        binding.tvAppPackageName.setText(item.getPackageName());
        binding.tvAppVersion.setText(String.format("%s（%s）", item.getVersionName(), item.getVersionCode()));
        GmSpaceObject.startCompatibleApplication(item);
    }
```

### 4. 获取已安装的应用列表 包含32位和64位  按lastUpdateTime排序

```kotlin
    GmSpaceObject.getInstalledCompatiblePackages()
```

### 5. 具体的安装、卸载、启动页面布局自定义 在对应的Activity实现即可
```md 
    // 设置32位插件信息
    GmSpaceObject.set32BitExtConfig(new GmSpace32BitExtConfig(
        "com.gmspace.sdk", // 包名
        "com.gmspace.ext.PluginInstallActivity", 
        "com.gmspace.ext.PluginLaunchActivity",
        "com.gmspace.ext.PluginUnInstallActivity"
    ))
```