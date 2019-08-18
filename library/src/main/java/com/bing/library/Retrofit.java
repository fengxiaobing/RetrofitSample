package com.bing.library;

import com.bing.library.http.GET;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/18
 * 描述：
 */
public class Retrofit {
    //接口的请求地址
    private HttpUrl baseUrl;
    //OkHttpClient唯一实现接口
    private Call.Factory callFactory;
    //key:如host.get()   value：该方法的属性封装类
    private Map<Method, ServiceMethod> serviceMethodCache = new HashMap<>();

    //因为使用构建者模式，所以这里不能直接被外界访问，只能用private或者缺失
    private Retrofit(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.callFactory = builder.callFactory;
    }

    public HttpUrl getBaseUrl() {
        return baseUrl;
    }

    public Call.Factory getCallFactory() {
        return callFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //这里可以获取HOST的所有的类,可以理解为是做了一个切面
                //获取方法名称
                System.out.println("获取方法名称>>>>" + method.getName());
                //获取方法的注解
                GET get = method.getAnnotation(GET.class);
                //获取方法的注解值
                System.out.println("获取方法的注解值>>>" + get.value());
                //获取方法的参数的注解
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for (Annotation[] annotation : parameterAnnotations) {
                    System.out.println("获取方法参数的注解>>>>" + Arrays.asList(annotation));
                }
                //获取方法的参数值
                System.out.println("获取方法的参数值>>>>" + Arrays.asList(args));

                //获取方法的所有内容：方法名、方法的注解、方法参数的注解、方法的参数
                //将method所有信息拦截后，存储到ServiceMethod（javaBean的实体类中）
                ServiceMethod serviceMethod = loadServiceMethod(method);

                return new OkhttpCall(serviceMethod, args);
            }
        });
    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result = serviceMethodCache.get(method);
        if (result != null) {
            return result;
        }
        //线程安全同步锁
        synchronized (serviceMethodCache) {
            //为什么需要拿两次
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this,method).build();
                serviceMethodCache.put(method, result);
            }

        }

        return result;
    }

    public static class Builder {
        //接口的请求地址
        private HttpUrl baseUrl;
        //OkHttpClient唯一实现接口
        private Call.Factory callFactory;

        //对外提供的方法入口
        public Builder baseUrl(String baseUrl) {
            if (baseUrl.isEmpty()) {
                throw new NullPointerException("baseUrl 不能为空");
            }
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(HttpUrl baseUrl) {
            if (baseUrl == null) {
                throw new NullPointerException("baseUrl 不能为空");
            }
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder callFactory(Call.Factory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        //属性的校验或者初始化
        public Retrofit build() {
            if (baseUrl == null) {
                throw new IllegalArgumentException("baseUrl 为空");
            }
            if (callFactory == null) {
                callFactory = new OkHttpClient();
            }
            return new Retrofit(this);
        }

    }
}
