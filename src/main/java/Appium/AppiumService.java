package Appium;

import Utility.Thread.ThreadUtil;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AnsonLiao on 17/11/15.
 * @author AnsonLiao
 */
public class AppiumService {

    private static Map<Long, AppiumDriverLocalService> serviceMap = new HashMap<Long, AppiumDriverLocalService>();
//    private AppiumDriverLocalService service = null;

    public static AppiumDriverLocalService getService() {
        Long threadID = ThreadUtil.getCurrentThreadID();
        if (!serviceMap.containsKey(threadID)) {
            AppiumDriverLocalService service = AppiumDriverLocalService.buildDefaultService();
            serviceMap.put(threadID, service);
        }

        return serviceMap.get(threadID);
    }

    public static void startService() {
        if (!serviceMap.containsKey(ThreadUtil.getCurrentThreadID())) {
            AppiumDriverLocalService service = getService();
            service.start();
        }
        else {
            serviceMap.get(ThreadUtil.getCurrentThreadID()).start();
        }

    }

    public static boolean serviceIsRunning() {
        return serviceIsRunning(ThreadUtil.getCurrentThreadID())?true:false;
    }

    public static boolean serviceIsRunning(Long threadID) {
        if (serviceMap.containsKey(threadID)) {
            return serviceMap.get(threadID).isRunning()?true:false;
        }
        else {
            return false;
        }
    }

    public static void stopService() {
        if (serviceMap.containsKey(ThreadUtil.getCurrentThreadID())) {
            stopService(ThreadUtil.getCurrentThreadID());
        }
    }

    public static void stopService(Long threadID) {
        if (serviceMap.containsKey(threadID)) {
            serviceMap.get(threadID).stop();
        }
    }

}
