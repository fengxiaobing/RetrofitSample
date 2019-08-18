package com.bing.library;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/18
 * 描述：请求构建体
 */
public class RequestBuilder {
    //方法的请求方式GET POST
    private String method;
    //接口的请求地址
    private HttpUrl baseUrl;
    //方法的注解值（"/ip/newIp"）
    private String relativeUrl;
    //请求url构建者（构建完整的请求url）
    private HttpUrl.Builder urlBuilder;
    //Form表单构建者
    private FormBody.Builder formBuilder;
    //构建完整的请求（包含url、method、body）
private Request.Builder requestBuilder;

    public RequestBuilder(String method,HttpUrl baseUrl, String relativeUrl,boolean hasBody) {
        this.method = method;
        this.baseUrl = baseUrl;
        this.relativeUrl = relativeUrl;
        //初始化请求
        requestBuilder = new Request.Builder();
        //是否根据请求实例化Form表单构建者
        if (hasBody) formBuilder = new FormBody.Builder();
    }
    //拼接QUery参数变量
    void addQueryParams(String name,String value){
        if(relativeUrl!=null){
            //baseUrl+方法注解中的url
            urlBuilder = baseUrl.newBuilder(relativeUrl);
            if(urlBuilder == null){
                throw new IllegalArgumentException("");
            }
            //每次请求都实例化了一次，重置
            relativeUrl = null;
        }
        urlBuilder.addQueryParameter(name,value);
    }
    //拼接Field参数
    void addFOrmField(String name,String value){
        formBuilder.add(name, value);
    }
    Request build(){
        //定义局部变量：1、每次保证值不一样 2、易回收
        HttpUrl url;
        if(urlBuilder!=null){
            url = urlBuilder.build();
        }else {
            url = baseUrl.resolve(relativeUrl);
            if(url == null){
                throw new IllegalArgumentException("");
            }
        }
        //如果有请求体，构造方法中会初始化Form表单构建者，然后再实例化请求体
        RequestBody requestBody = null;
        if(formBuilder!=null){
            requestBody = formBuilder.build();
        }
        //构建完整请求
        return requestBuilder
                .url(url)
                .method(method,requestBody)
                .build();
    }
}
