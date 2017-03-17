package com.example.administrator.netword.Bean;

/**
 * Created by Administrator on 2017/3/17.
 */

public class Data {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Data(String name, String url) {

        this.name = name;
        this.url = url;
    }
}
