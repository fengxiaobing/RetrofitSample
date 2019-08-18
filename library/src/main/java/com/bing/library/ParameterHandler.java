package com.bing.library;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/18
 * 描述：
 */
public abstract class ParameterHandler {

    abstract void apply(RequestBuilder requestBuilder, String value);

    static final class Query extends ParameterHandler{
        String name;
        Query(String name){
            this.name = name;
        }
        @Override
        void apply(RequestBuilder requestBuilder, String value) {
            if(value == null)return;
            requestBuilder.addQueryParams(name,value);

        }
    }
    static final class Field extends ParameterHandler{
        String name;
        Field(String name){
            this.name = name;
        }
        @Override
        void apply(RequestBuilder requestBuilder, String value) {
            if(value == null)return;
            requestBuilder.addFOrmField(name,value);
        }
    }
}
