package lee.bottle.lib.toolset.os;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import lee.bottle.lib.toolset.log.LLog;


/**
 * Created by Leeping on 2019/6/21.
 * email: 793065165@qq.com
 */
public class NotifyUer {

    private static int currentId =  1;

    private static String app_name = "";
    private static int app_icon_rid = 0;

    public static void init(Context context,String app_name,int app_icon) {
        NotifyUer.app_name = app_name;
        NotifyUer.app_icon_rid = app_icon;
    }

    public static void createMessageNotify(Context context, String message, Intent intent) {
        LLog.print("创建通知栏消息");
        FrontNotification.Build build = new FrontNotification.Build(context).setId(currentId++);
        build.setFlags(new int[]{Notification.FLAG_SHOW_LIGHTS,Notification.FLAG_AUTO_CANCEL});
        build.setActivityIntent(intent);
        build.setGroup("messageList");
        build.setDefaults(Notification.DEFAULT_ALL);
        build.setIcon(app_icon_rid);
        build.setText(app_name,message,"点击进入");
        build.setWhen(System.currentTimeMillis());
        build.setTicker(message);
        build.generateNotification().showNotification();
    }

    public static void createMessageNotifyTips(final Context context, String message, Intent intent) {
        LLog.print("创建通知提示: "+ context +" , "+ message);
        final int channelID = currentId++;
        FrontNotification.Build build = new FrontNotification.Build(context).setId(channelID);
        build.setFlags(new int[]{Notification.FLAG_SHOW_LIGHTS,Notification.FLAG_AUTO_CANCEL});
        build.setGroup("messageTipsList");
        build.setDefaults(Notification.DEFAULT_ALL);
        build.setIcon(app_icon_rid);
        build.setText(app_name,message,"新消息");
        build.setWhen(System.currentTimeMillis());
        build.setTicker(message);
        build.setExpandText(message);
        build.setActivityIntent(intent);
        build.generateNotification().showNotification();
    }

    public static FrontNotification createDownloadApkNotify(Context context,String title,Intent intent){

       return new FrontNotification.Build(context).setLevel(3)
                .setId(currentId++)
                .setGroup("download-"+title)
                .setDefaults(Notification.DEFAULT_ALL)
                .setActivityIntent(intent)
                .setSmallIcon(app_icon_rid)
                .setText(app_name,title ,"下载完成自动关闭")
                .setFlags(new int[]{
                       Notification.FLAG_FOREGROUND_SERVICE,
                       Notification.FLAG_ONLY_ALERT_ONCE,
                       Notification.FLAG_ONGOING_EVENT,
                       Notification.FLAG_NO_CLEAR})
                .generateNotification();
    }

    public static void cacheAllMessageByExistNotifySpecID(Context context,int channelID){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(channelID);
    }

    public static void cacheAllMessageByExistNotify(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

}
