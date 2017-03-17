package com.example.administrator.netword;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.netword.Bean.AppBean;
import com.example.administrator.netword.Bean.Data;
import com.example.administrator.netword.uilt.IsNet;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private boolean net;
    private boolean wife;
    private List<Data> dataList=new ArrayList<>();
    private ListView listView;
    private TextView down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        down = (TextView)findViewById(R.id.down);
        netWork();
    }
    public void netWork() {
        net = IsNet.isNet(MainActivity.this);
        wife = IsNet.isWife(MainActivity.this);
        if(!net) {
            String[] arr=new String[]{"移动数据","WIFE"};
            Toast.makeText(MainActivity.this, "网络已连接", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("请选择");
            builder.setCancelable(false);
            builder.setSingleChoiceItems(arr, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case 1 :
                            if(wife)
                            {
                                Toast.makeText(MainActivity.this, "下载", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                setGson();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("提示");
                                builder.setMessage("您当前还没开wife，是否打开wife");
                                builder.setCancelable(false);
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                                        startActivity(wifiSettingsIntent);
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create();
                                builder.show();
                            }
                            break;
                        case 0 :
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("提示");
                            builder.setMessage("您当前正在使用移动数据，是否要进行下载");
                            builder.setCancelable(false);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "下载", Toast.LENGTH_SHORT).show();
                                    setGson();
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create();
                            builder.show();
                            break;
                    }
                }
            }).show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("提示");
            builder.setMessage("网络未连接，请设置网络");
            builder.setCancelable(false);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create();
            builder.show();
        }
    }

    private void setGson() {
        String path="http://mapp.qzone.qq.com/cgi-bin/mapp/mapp_subcatelist_qq?yyb_cateid=-10&categoryName=腾讯软件&pageNo=1&pageSize=20&type=app&platform=touch&network_type=unknown&resolution=412x732";
        x.http().get(new RequestParams(path), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String substring = result.substring(0, result.length() - 1);
                Gson gson = new Gson();
                AppBean bean = gson.fromJson(substring,AppBean.class);
                if(bean!=null)
                {
                    List<AppBean.Bean> app = bean.getApp();
                    String message = bean.getMessage();
                    Log.d("zzz", message);
                    for(AppBean.Bean a: app)
                    {
                        String name = a.getName();
                        String url = a.getUrl();
                        Log.d("zzz", name + "     " + url);
                        Data data = new Data(name, url);
                        dataList.add(data);
                        Log.d("zzz", dataList.toString());
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.d("zzz", "onFinished");
                listView.setAdapter(new MyAdataer(MainActivity.this,dataList));
            }
        });
    }
}
