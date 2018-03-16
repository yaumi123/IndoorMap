package com.tq.simple_retrofit;

/**
 * Created by tony on 2017/1/11.
 */

public class DefRetrofit {

    public static DefRetrofit getDef() {
        return new DefRetrofit();
    }

    private String BASE_URL = "";

    public DefRetrofit setBaseUrl(String baseUrl) {
        this.BASE_URL = baseUrl;
        return this;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }


}
