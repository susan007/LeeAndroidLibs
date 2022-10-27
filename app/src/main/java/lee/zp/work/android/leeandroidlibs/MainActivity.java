package lee.zp.work.android.leeandroidlibs;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import lee.bottle.lib.toolset.log.LLog;
import lee.bottle.lib.toolset.os.BaseActivity;
import lee.bottle.lib.webh5.JSInterface;
import lee.bottle.lib.webh5.SysWebView;
import lee.zp.work.android.leeandroidlibs.jsbridge.CommJSInterface;

import static lee.zp.work.android.leeandroidlibs.jsbridge.WebViewSetting.addErrorMonitor;


public class MainActivity extends BaseActivity {

    // 底层图层
    private FrameLayout frameLayout;
    // 浏览器
    private SysWebView webView;
    // 地址
    private final String indexURL = "file:///android_asset/index.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(0,0));
        setContentView(frameLayout);

        webView = new SysWebView(this);
        webView.bind(this,frameLayout);

        webView.jsInterface.addJavascriptInterface(
                createJSInterface()
        );

        addErrorMonitor(webView,indexURL);
        webView.open(indexURL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        webView.onActivityResultHandle(requestCode,resultCode,data);
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        webView.unbind();
        webView = null;
        super.onDestroy();
    }

    // 捕获返回键 处理
    @Override
    public void onBackPressed() {
        cur_back_time = 0;
        isExitApplication = false;
        super.onBackPressed();
    }

    private JSInterface.DefaultFunction createJSInterface(){
        return new CommJSInterface(this, webView){

            @JavascriptInterface
            @Override
            public void openWindow(String url, String type) {
                if (url.startsWith("appbox://main")){
                    startMainActivity();
                    return;
                }
                super.openWindow(url, type);
            }

            @JavascriptInterface
            @Override
            public void onInitializationComplete() {
                // 展开布局
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LLog.print("登录页加载完成...........................");
                        // 移除背景资源
                        getWindow().getDecorView().setBackgroundResource(0);
                        frameLayout.getLayoutParams().height =-1;
                        frameLayout.getLayoutParams().width =-1;
                        frameLayout.requestLayout();
                    }
                });
            }
        };
    }



    private void startMainActivity() {
        Intent intent = new Intent(this,MainFragmentActivity.class);
        startActivity(intent);
        finish();
    }


}