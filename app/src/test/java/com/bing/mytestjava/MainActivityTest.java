package com.bing.mytestjava;

import com.bing.library.Retrofit;
import com.bing.library.http.GET;
import com.bing.library.http.POST;
import com.bing.library.http.Query;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


import static org.junit.Assert.*;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/18
 * 描述：
 */
public class MainActivityTest {

    public static String IP = "144.24.161.97";
    public static String KEY = "aa205eeb45aa76c6afe3c52151b52160";
    public static String BASE_URL = "http://apis.juhe.cn/";


    interface HOST {
        @GET("/ip/ipNew")
        Call get(@Query("ip") String ip, @Query("key") String key);


    }


    @Test
    public void test() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        HOST host = retrofit.create(HOST.class);
        Call call = host.get(IP, KEY);
     try {
            Response response = call.execute();
            if(response!= null && response.isSuccessful()){
                System.out.println("成功"+response.body().string());
            }else {
                System.out.println("失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}