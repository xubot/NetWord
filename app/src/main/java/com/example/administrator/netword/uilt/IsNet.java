package com.example.administrator.netword.uilt;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/17.
 */

public class IsNet {
    public static boolean isNet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //判断是否联网
        if(networkInfo!=null&&networkInfo.getType() == connectivityManager.TYPE_MOBILE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static boolean isWife(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //networkInfo.getType() == connectivityManager.TYPE_WIFI
        //判断是否联网
        if(networkInfo!=null&&networkInfo.getType() == connectivityManager.TYPE_WIFI)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
