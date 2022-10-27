package lee.zp.work.android.leeandroidlibs;

import android.app.Activity;
import android.os.Bundle;

import lee.bottle.lib.toolset.http.FileServerClient;
import lee.bottle.lib.toolset.log.LLog;
import lee.bottle.lib.toolset.os.ApplicationAbs;
import lee.bottle.lib.toolset.os.ApplicationDevInfo;
import lee.bottle.lib.toolset.os.NotifyUer;
import lee.bottle.lib.webh5.SysWebViewSetting;

public class MainApplication extends ApplicationAbs {

    @Override
    protected void onCreateByAllProgress(String processName) {
        super.onCreateByAllProgress(processName);
    }

    @Override
    protected void onCreateByApplicationMainProgress(String processName) {
        // 处理webview
        SysWebViewSetting.initGlobalSetting(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        super.onActivityCreated(activity, savedInstanceState);
        LLog.print(activity + " *************** onCreate ");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        super.onActivityStarted(activity);
        LLog.print(activity + " *************** onStart ");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        super.onActivityResumed(activity);
        LLog.print(activity + " *************** onResumed ");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        super.onActivityPaused(activity);
        LLog.print(activity + " *************** onPaused ");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        super.onActivitySaveInstanceState(activity, outState);
        LLog.print(activity + " *************** onSaveInstanceState ");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        super.onActivityStopped(activity);
        LLog.print(activity + " *************** onStop ");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        super.onActivityDestroyed(activity);
        LLog.print(activity + " *************** onDestroyed ");
    }
}
