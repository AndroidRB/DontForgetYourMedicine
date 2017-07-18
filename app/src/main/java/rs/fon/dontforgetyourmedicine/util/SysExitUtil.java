package rs.fon.dontforgetyourmedicine.util;

import android.app.Activity;

import java.util.ArrayList;

public class SysExitUtil {
    public static ArrayList<Object> activityList;

    static {
        activityList = new ArrayList();
    }

    public static void exit() {
        int siz = activityList.size();
        for (int i = 0; i < siz; i++) {
            if (activityList.get(i) != null) {
                ((Activity) activityList.get(i)).finish();
            }
        }
    }

    public static int getSize() {
        return activityList.size();
    }
}
