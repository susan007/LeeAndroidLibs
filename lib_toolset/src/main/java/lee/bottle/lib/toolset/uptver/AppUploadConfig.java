package lee.bottle.lib.toolset.uptver;

import lee.bottle.lib.toolset.http.FileServerClient;
import lee.bottle.lib.toolset.log.LLog;
import lee.bottle.lib.toolset.util.GsonUtils;
import lee.bottle.lib.toolset.util.StringUtils;

import static lee.bottle.lib.toolset.http.FileServerClient.downFileURL;

/** 服务APP升级配置 */
public class AppUploadConfig {
    public static String CONFIG_JSON_FILE_PATH = "/config.json";
    int serverVersion = 0;
    String updateMessage = "发现新版本,请更新!";
    String apkLink = "/drug.apk";
    int forceUpdate = 0; //是否强制更新

    private static String convertToFileServerURL(String path){
        if (!StringUtils.isEmpty(path)
                && (!path.startsWith("http") && !path.startsWith("https"))){
            return downFileURL(path);
        }
        return path;
    }

    // 加载服务器配置信息URL
    private static String _loadServerConfigJson(int retry,int max) {
        String url = convertToFileServerURL(CONFIG_JSON_FILE_PATH);
        try {
            LLog.print("服务器APK版本请求:\t" + url);
            String json = FileServerClient.text(url).trim().replaceAll("\\s*","");
            LLog.print("服务器APK版本响应:\t"+json);
            return json;
        } catch (Exception e) {
            LLog.print(retry+"/"+max+" 加载服务器配置信息失败,URL="+ url +" ,错误原因:\n"+e);
            if (retry < max){
                return _loadServerConfigJson(++retry,max);
            }
        }
        return null;
    }

    // 更新服务配置
    static AppUploadConfig load(){
        String json = _loadServerConfigJson(0,10);
        AppUploadConfig config  =  GsonUtils.jsonToJavaBean(json, AppUploadConfig.class);
        if (config == null) config = new AppUploadConfig();
        config.apkLink = convertToFileServerURL(config.apkLink);
        return config;
    }

}
