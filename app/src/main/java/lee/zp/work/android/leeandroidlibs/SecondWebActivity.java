package lee.zp.work.android.leeandroidlibs;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

import lee.bottle.lib.toolset.os.BaseActivity;
import lee.bottle.lib.webh5.SysWebView;
import lee.bottle.lib.webh5.interfaces.WebProgressI;


public class SecondWebActivity extends BaseActivity {

    //底层图层
    protected FrameLayout frameLayout;
    // 浏览器
    protected SysWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String indexURL = null;
        Intent intent = getIntent();
        if (intent != null){
            String url =  intent.getStringExtra("url");
            if (url != null){
                indexURL = url;
            }
        }
        if (indexURL == null) {
            finish();
            return;
        }

        frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        setContentView(frameLayout);

        webView = new SysWebView(this);

        // 进度条
        final CircleProgressDialog circleProgressDialog = new CircleProgressDialog(this);
        circleProgressDialog.showDialog();
        // 加载进度监听
        webView.webProgressI = new WebProgressI() {
            @Override
            public void updateProgress(String url, int current, boolean isManual) {
                      if (current>=100) circleProgressDialog.dismiss();
            }
        };

        webView.bind(this,frameLayout);

        webView.open(indexURL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        webView.onActivityResultHandle(requestCode,resultCode,data);
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        if (webView!=null){
            webView.unbindActivity();
            webView.close(true,true);
            webView = null;
        }

        super.onDestroy();
    }


    // 捕获返回键 处理
    @Override
    public void onBackPressed() {
        if (webView.onBackPressed()) return;
        cur_back_time = 0;
        isExitApplication = false;
        super.onBackPressed();
    }



}