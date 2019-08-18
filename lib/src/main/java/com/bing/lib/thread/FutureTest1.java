package com.bing.lib.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/3
 * 描述：
 */
public class FutureTest1 {
    public static void main(String[] args) {
        //  FutureTask<V> V代表异步任务返回值的类型
        Task worker = new Task();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(worker) {
            //异步任务执行完毕后的回调
            @Override
            protected void done() {
                try {
                    System.out.println("done" + get()+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        //futureTask一般会被线程池执行
        //线程池 使用预定义配置
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(futureTask);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////取消
//        futureTask.cancel(true);


        try {
            //futureTask.get()  获取异步任务的返回值   是一个阻塞等方法  等异步任务执行完毕才会执行
            Integer integer = futureTask.get();
            System.out.println(integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //异步任务
    static class Task implements Callable<Integer> {
        //返回异步任务的执行结果
        @Override
        public Integer call() throws Exception {
            int i = 0;
            for (; i < 10; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + "_" + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return i;
        }
    }

}
