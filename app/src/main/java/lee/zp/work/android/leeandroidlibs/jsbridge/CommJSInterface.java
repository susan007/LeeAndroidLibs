package lee.zp.work.android.leeandroidlibs.jsbridge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;


import lee.bottle.lib.toolset.uptver.UpdateVersionServerImp;
import lee.bottle.lib.webh5.JSInterface;
import lee.bottle.lib.webh5.SysWebView;
import lee.zp.work.android.leeandroidlibs.SecondWebActivity;

public class CommJSInterface extends JSInterface.DefaultFunction {
    public Context context;

    public CommJSInterface(Context context, SysWebView sysWebView) {
        super(sysWebView.jsInterface);
        this.context = context;
    }

    /* type:
     * push 压入一个新页面 ;
     * pushAndRemove 移除当前页 打开新页面 ;
     * pushAndRemoveAll 移除所有历史页面 打开新页面
     * */
    @JavascriptInterface
    @Override
    public void openWindow(String url,String type){
        Intent intent = new Intent(context, SecondWebActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @JavascriptInterface
    @Override
    public void checkVersion() {
        if (context instanceof Activity){
            UpdateVersionServerImp.checkVersion((Activity)context,true);
        }
    }
}
