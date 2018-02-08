package com.example.hasee.wq.tools;

/**
 * Created by wangqing on 2018/2/8.
 */

public class FastClick {
    //上次点击的时间
    private static long lastClickTime=0;
    //时间间隔
    private static int spaceTime=1000;

    public static boolean isFastClick(){
        //获取当前系统的时间
        long currentTime=System.currentTimeMillis();
        //是否允许点击；
        boolean fastClick;
        fastClick=(currentTime-lastClickTime<spaceTime);
        lastClickTime=currentTime;
        return fastClick;
    }

}
