package lee.zp.work.android.leeandroidlibs.jsbridge;

import android.net.Uri;

import lee.bottle.lib.toolset.log.LLog;
import lee.bottle.lib.toolset.util.AppUtils;
import lee.bottle.lib.webh5.SysWebView;

public class WebViewSetting {
    // 添加错误监听
    public static void addErrorMonitor(final SysWebView sysWebView, final String indexURL){
        sysWebView.loadErrorI = (webView, webResourceRequest, webResourceError) -> {
            Uri uri =  webResourceRequest.getUrl();
            LLog.print("打开网页错误: " + uri);
            if (!AppUtils.isNetworkAvailable(webView.getContext())){
                String str = "file:///android_asset/error.html?reloadUrl="+ indexURL;
                webView.loadUrl(str);
            }
        };
    }

}
