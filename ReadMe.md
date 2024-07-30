### 1. 安装应用
```java
  new DialogAsyncTask<String, String, GmSpaceResultParcel>(this) {
            @Override
            protected void onPreExecute() {
                super.showProgressDialog("正在安装");
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.updateProgressDialog(values[0]);
            }

            @Override
            protected GmSpaceResultParcel doInBackground(String... uris) {
                String uri = uris[0];
                // 无需额外判断 调用会处理当前宿主是否支持的游戏
                // activity接受32位回调  统一在下面的回调内处理 
                GmSpaceObject.installCompatiblePackage(activity,uri,null);
                return resultParcel;
            }
            @Override
            protected void onPostExecute(GmSpaceResultParcel result) {
                super.onPostExecute(result);
//                if(result.getCode() == GmSpaceResultParcel.CODE_32BIT_PACKAGE_INSTALLing) {
//                    setSubtitle("正在使用32位游戏插件进行安装！");
//                } else if(result.getCode() == GmSpaceResultParcel.CODE_32BIT_PACKAGE_UNINSTALL) {
//                    setSubtitle("请先安装32位游戏插件！");
//                    // 下载32位应用安装后重试
//                } else if (result.isSucceed()) {
//                    setSubtitle("应用安装成功 " + (result.getData() == null ? "" : result.getData().getString(GmSpaceEvent.KEY_PACKAGE_NAME)));
//                } else {
//                    setSubtitle("应用安装失败 " + result.getMessage());
//                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, src.getAbsolutePath());
```
> 安装回调（在统一回调内处理）
```md
    // 当前宿主支持的游戏可以直接通过    GmSpaceResultParcel resultParcel的isSucceed判断 32位不支持
    GmSpaceObject.registerGmSpaceCompatibleEventListener(new OnGmSpaceReceivedEventListener() {
        @Override
        public void onReceivedEvent(int type, Bundle extras) {
            if (GmSpaceEvent.TYPE_PACKAGE_INSTALLED == type) {
                // 有应用安装 返回安装的应用信息 （KEY_PACKAGE_COMPATIBLE_STATUS 可以获取安装是否成功）
                AppItemEnhance appItemEnhance = extras.getParcelable(GmSpaceEvent.KEY_PACKAGE_COMPATIBLE_INFO);
                // appItemEnhance.isOverride() 是否重复安装
                // appItemEnhance.isExt32() 是否32位

                // GmSpaceEvent.KEY_PACKAGE_COMPATIBLE_INSTALL_RESULT 获取返回结果GmSpaceResultParcel 具体原因等
            } else if (GmSpaceEvent.TYPE_PACKAGE_UNINSTALLED == type) {
                // 有应用卸载 （KEY_PACKAGE_COMPATIBLE_STATUS 可以获取卸载是否成功）
                final String packageName = extras.getString(GmSpaceEvent.KEY_PACKAGE_NAME);
            }else if (GmSpaceEvent.TYPE_COMPONENT_SETTING_CHANGE == type){
                // 组件状态变化 返回应用信息
                AppItemEnhance appItemEnhance = extras.getParcelable(GmSpaceEvent.KEY_PACKAGE_COMPATIBLE_INFO);
            }
        }
    });
```

### 2. 应用卸载
```md
        new DialogAsyncTask<Void, Void, Boolean>(context) {
            @Override
            protected void onPreExecute() {
                super.showProgressDialog("正在卸载");
            }

            @Override
            protected Boolean doInBackground(Void... voids) {
                // activity接受32位回调  统一在上诉回调内处理 通过status判断是否成功
                GmSpaceObject.uninstallCompatiblePackage(activity,appItemEnhance);
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
```

### 3. 应用启动
```md
        new AsyncTask<Void, Drawable, Void>() {
            @Override
            protected void onProgressUpdate(Drawable... values) {
                // 有windowBackground
//                final Drawable windowBackground = (Drawable) values[0];
//                applyWindowBackground(windowBackground);
            }

            @Override
            protected void onPreExecute() {
                GlideUtils.loadFadeSkipCache(binding.ivAppIcon, item.getIconUri());
                binding.tvAppName.setText(item.getAppName());
                binding.tvAppPackageName.setText(item.getPackageName());
                binding.tvAppVersion.setText(String.format("%s（%s）", item.getVersionName(), item.getVersionCode()));
            }

            @Override
            protected Void doInBackground(Void... voids) {
//                final Drawable drawable = GmSpaceUtils.getLaunchActivityWindowBackground(item.getPackageName());
//                publishProgress(drawable);

                // 启动app
                GmSpaceObject.startCompatibleApplication(item);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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