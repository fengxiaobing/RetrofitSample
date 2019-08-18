package com.bing.library;

import com.bing.library.http.Field;
import com.bing.library.http.GET;
import com.bing.library.http.POST;
import com.bing.library.http.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.HttpUrl;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/18
 * 描述：
 */
public class ServiceMethod {

    //OkHttpClient封装构建
    private Retrofit retrofit;
    //带注解的方法
    private Method method;
    //方法上所有的注解（有的方法可能有多个注解）
    private Annotation[] methodAnnotations;
    //方法参数的所有注解（一个方法有多个参数，一个参数可能有多个注解）
    private Annotation[][] parameterAnnotationsArray;
    //方法的请求方式（GET、POST）
    private String httpMetnod;
    //方法注解的值（"/ip/newIp"）
    private String relativeUrl;
    //方法参数的数组（每个对象包含：参数的注解值、参数值）
    private ParameterHandler[] parameterHandlers;
    //是否有请求体（GET方法没有）
    private boolean hasBody;
    //接口的请求地址
    private HttpUrl baseUrl;
    //OkHttpClient唯一实现接口
    private Call.Factory callFactory;
    private ServiceMethod(Builder builder) {
        this.callFactory = builder.retrofit.getCallFactory();
        this.baseUrl = builder.retrofit.getBaseUrl();
        this.relativeUrl = builder.relativeUrl;
        this.parameterHandlers = builder.parameterHandlers;
        this.hasBody = builder.hasBody;
        this.httpMetnod = builder.httpMetnod;

    }
//      host.get("ww","sdsd");
    public Call toCall(Object[] args) {

        //最终的拼装类
        RequestBuilder requestBuilder = new RequestBuilder(httpMetnod,baseUrl,relativeUrl,hasBody);

        ParameterHandler[] handlers = parameterHandlers;
        int argumentsCount = args!=null?args.length:0;
        //Proxy方法的参数个数是否等于参数的数组（手动添加），这里理解为校验
        if (argumentsCount != handlers.length){
            throw new IllegalArgumentException("添加的参数数量不对");
        }
        //循环拼接每个参数名和参数值
        for (int i = 0; i < argumentsCount; i++) {
            //方法参数的数组中每个对象已经调用了对象实现方法
            handlers[i].apply(requestBuilder,args[i].toString());
        }

        return callFactory.newCall(requestBuilder.build());
    }

    public static class Builder {
        //OkHttpClient封装构建
        private Retrofit retrofit;
        //带注解的方法
        private Method method;
        //方法上所有的注解（有的方法可能有多个注解）
        private Annotation[] methodAnnotations;
        //方法参数的所有注解（一个方法有多个参数，一个参数可能有多个注解）
        private Annotation[][] parameterAnnotationsArray;
        //方法的请求方式（GET、POST）
        private String httpMetnod;
        //方法注解的值（"/ip/newIp"）
        private String relativeUrl;
        //方法参数的数组（每个对象包含：参数的注解值、参数值）
        private ParameterHandler[] parameterHandlers;
        //是否有请求体（GET方法没有）
        private boolean hasBody;


        Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            //获取方法的所有注解(@GET @POST)
            methodAnnotations = method.getAnnotations();
            //获取方法参数的注解（@Query @Field）
            parameterAnnotationsArray = method.getParameterAnnotations();


        }

        public ServiceMethod build() {
        //遍历方法的每个注解
            for (Annotation annotation : methodAnnotations) {
                //把方法，方法的注解值都解析了
                parseMethodAnnotations(annotation);
            }
        //把方法的参数的注解，方法的参数  这里是双层循环
            //一个方法有多个参数，一个参数有多个注解
            //定义方法参数的数组长度
            int parameterCount = parameterAnnotationsArray.length;
            parameterHandlers = new ParameterHandler[parameterCount];
            //遍历方法的参数，我们只需要@Query或者@Field注解
            for (int i = 0; i < parameterCount; i++) {
                //获取每个参数的所有的注解
                Annotation[] parameterAnnotations = parameterAnnotationsArray[i];
                //如果该方法没有任何注解，则抛出异常
                if(parameterAnnotations == null){
                    throw new IllegalArgumentException("Retrofit的方法没有注解");
                }
                //参考源码，获取参数的注解值、参数值
                parameterHandlers[i] = parseParameter(i,parameterAnnotations);
            }

            return new ServiceMethod(this);
        }
//解析所有的注释，嵌套循环
        private ParameterHandler parseParameter(int i, Annotation[] annotations) {
            ParameterHandler result = null;
            //遍历参数的注解，如{@Query("ip) String ip}
            for (Annotation annotation : annotations) {
                //注解可能是Query或者Filed
                ParameterHandler annotationAction = parseParameterAnnotation(annotation);

                if (annotationAction == null) {
                    continue;
                }
                result = annotationAction;
            }
            return result;
        }
//解析参数的注解 可能是Query也可能是Field
        private ParameterHandler parseParameterAnnotation(Annotation annotation) {
            if(annotation instanceof Query){
                Query query = (Query) annotation;
                String name = query.value();
                //注意：传过去的参数是注解的值，非参数值，参数值通过Proxy方法传入
                return new ParameterHandler.Query(name);
            }else  if(annotation instanceof Field){
                Field field = (Field) annotation;
                String name = field.value();
                //注意：传过去的参数是注解的值，非参数值，参数值通过Proxy方法传入
                return new ParameterHandler.Field(name);
            }
            return null;
        }


        //解析方法的注解 可能是GET  可能是POST
        private void parseMethodAnnotations(Annotation annotation) {
            if (annotation instanceof GET) {
                //@GET("/ip/newIp")
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            }
        }

        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody) {
            //方法的请求方式
            this.httpMetnod = httpMethod;
            //方法的注解的值 "/ip/newIp"
            this.relativeUrl = value;
            //方法是否有请求体
            this.hasBody = hasBody;
        }
    }
}
