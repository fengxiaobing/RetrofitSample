package com.bing.lib.thread;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/3
 * 描述：
 */
public class AcyncTaskTest {
    public static void main(String[] args) {
        int CPU_COUNT = Runtime.getRuntime().availableProcessors(); //java虚拟机可用的cpu的个数
        int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
        int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
        int KEEP_ALIVE_SECONDS = 30;
//线程工厂
         ThreadFactory sThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                String name = "Thread #" + mCount.getAndIncrement();
                System.out.println(name);
                return new Thread(r, name);
            }
        };
//任务队列(128)
       BlockingQueue<Runnable> sPoolWorkQueue =
                new LinkedBlockingQueue<Runnable>(128);
        Executor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);

        //执行异步任务
        for (int i = 0; i < 200; i++) {
            threadPoolExecutor.execute(new MyTask());
        }

    }

    static class MyTask implements Runnable{
        @Override
        public void run() {
            while (true){
//                System.out.println("======"+Thread.currentThread().getName());
                try {
                    System.out.println("======"+Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
