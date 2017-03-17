package com.example.administrator.netword.uilt;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2017/3/17.
 */

public class AppData extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
