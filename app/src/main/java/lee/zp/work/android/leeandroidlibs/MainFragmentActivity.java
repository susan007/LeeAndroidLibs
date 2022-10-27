package lee.zp.work.android.leeandroidlibs;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

import lee.bottle.lib.toolset.log.LLog;
import lee.bottle.lib.toolset.os.BaseActivity;
import lee.bottle.lib.toolset.util.AppUtils;
import lee.bottle.lib.webh5.SysWebView;
import lee.zp.work.android.leeandroidlibs.jsbridge.CommJSInterface;

public class MainFragmentActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {



    private SysWebView[] webViews = new SysWebView[4];

    private FrameLayout frameLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 进度条
        final CircleProgressDialog circleProgressDialog = new CircleProgressDialog(this);
        circleProgressDialog.showDialog();

        setContentView(R.layout.main_fragment_activity);
        BottomNavigationView buttonTab = findViewById(R.id.bottom_navigation_view);
        buttonTab.setOnNavigationItemSelectedListener(this);
        frameLayout = findViewById(R.id.fragment_layout);
        buttonTab.setItemIconTintList(null);
        initWebViews();
        buttonTab.setSelectedItemId(R.id.navigation_work);
        circleProgressDialog.dismiss();
    }



    private void initWebViews() {

        for (int i=0;i<4;i++){
            String indexURL = "file:///android_asset/tab"+(i+1)+".html";
            SysWebView webView = new SysWebView(this);
            webView.bindActivity(this);

            webView.jsInterface.addJavascriptInterface(
                    new CommJSInterface(this, webView){
                        @JavascriptInterface
                        @Override
                        public void onInitializationComplete() {
                            AppUtils.toastLong(MainFragmentActivity.this,webView.getUrl() +" 初始化完成");
                        }
                    }
            );

//            addErrorMonitor(webView,indexURL);
            webView.open(indexURL);
            webViews[i] = webView;
        }

    }


    private void showWebViews(int i) {
        for (SysWebView webView:webViews){
            webView.unbindViewGroup();
        }
        webViews[i].bindViewGroup(frameLayout);
    }

    /* 底部导航栏点击事件 */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        LLog.print("底部导航栏点击: "+ item);

        switch (item.getItemId()){

            case R.id.navigation_work:
                showWebViews(0);
                break;
            case R.id.navigation_msg:
                showWebViews(1);
                break;
            case R.id.navigation_rep:
                showWebViews(2);
                break;
            case R.id.navigation_my:
                showWebViews(3);
                break;
        }

        return true;
    }
}
