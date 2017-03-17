package com.example.administrator.netword;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.netword.Bean.Data;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class MyAdataer extends BaseAdapter{
    private Context context;
    private List<Data> dataList;
    private int convert;
    private TextView name;
    private TextView down;

    public MyAdataer(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context,R.layout.listview,null);
        name = (TextView) convertView.findViewById(R.id.name);
        down = (TextView) convertView.findViewById(R.id.down);
        name.setText(dataList.get(position).getName().toString());
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url =dataList.get(position).getUrl();
                RequestParams params = new RequestParams(url);
                //保存到sdcard
                params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/app/");
                //自动文件命令
                params.setAutoRename(true);
                //下载
                x.http().post(params, new Callback.ProgressCallback<File>() {

                    private int currentsum;
                    private int totalsum;
                    private ProgressDialog progressDialog = new ProgressDialog(context);;

                    @Override
                    public void onWaiting() {
                        Log.d("zzz11","onWaiting");
                    }

                    @Override
                    public void onStarted() {
                        Log.d("zzz11","onStarted");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        //文件总大小和当前进度
                        totalsum = new Long(total).intValue();
                        currentsum = new Long(current).intValue();

                        progressDialog.setMessage("正在下载当前应用 ,请耐心等待");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.show();

                        progressDialog.setMax(totalsum);
                        progressDialog.setProgress(currentsum);
                    }

                    @Override
                    public void onSuccess(File result) {
                        if(totalsum==currentsum)
                        {
                            progressDialog.setProgress(0);
                        }
                        progressDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                        context.startActivity(intent);
                        Toast.makeText(context, "安装成功", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }
                    @Override
                    public void onCancelled(CancelledException cex) {

                    }
                    @Override
                    public void onFinished() {
                        Log.d("zzz11", "onFinished");
                    }
                });
            }
        });
        return convertView;
    }
}
