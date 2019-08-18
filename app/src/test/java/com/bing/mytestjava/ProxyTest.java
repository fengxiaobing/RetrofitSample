package com.bing.mytestjava;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/18
 * 描述：
 */
public class ProxyTest {
    interface HOST {
        @GET("/ip/ipNew")
        Call<ResponseBody> get(@Query("ip") String ip, @Query("key") String key);

        @POST("/ip/ipNew")
        @FormUrlEncoded
        Call<ResponseBody> post(@Query("ip") String ip, @Query("key") String key);
    }
    @Test
    public void proxy(){
       HOST host = (HOST) Proxy.newProxyInstance(HOST.class.getClassLoader(), new Class[]{HOST.class}, new InvocationHandler() {
           @Override
           public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               //这里可以获取HOST的所有的类,可以理解为是做了一个切面
               //获取方法名称
               System.out.println("获取方法名称>>>>"+method.getName());
               //获取方法的注解
               GET get = method.getAnnotation(GET.class);
               //获取方法的注解值
               System.out.println("获取方法的注解值>>>"+get.value());
               //获取方法的参数的注解
               Annotation[][] parameterAnnotations = method.getParameterAnnotations();
               for (Annotation[] annotation : parameterAnnotations) {
                   System.out.println("获取方法参数的注解>>>>"+ Arrays.asList(annotation));
               }
               //获取方法的参数值
               System.out.println("获取方法的参数值>>>>"+Arrays.asList(args));
               return null;
           }
       });
       //$Proxy4,运行时创建的类，存在于内存当中。
       host.get("ww","sdsd");
    }
}
