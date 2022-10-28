package lee.bottle.lib.toolset.uptver;

import android.app.Activity;

import java.io.File;

public interface UpdateVersionPromptBox {
    void callback(boolean isForceUpdate , final Activity activity, final File file, String updateMessage, final String apkUrl);
}
