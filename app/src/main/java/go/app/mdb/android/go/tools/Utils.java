package go.app.mdb.android.go.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Utils {
    // 两次点击按钮之间的点击间隔不能少于4000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }
    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }
    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    //获取版本info
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
