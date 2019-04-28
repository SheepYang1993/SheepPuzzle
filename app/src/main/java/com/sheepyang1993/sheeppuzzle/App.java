package com.sheepyang1993.sheeppuzzle;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * @author SheepYang
 * @email 332594623@qq.com
 * @date 2019/4/28 9:47
 * @describe App程序启动入口
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
