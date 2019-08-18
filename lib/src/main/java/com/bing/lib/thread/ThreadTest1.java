package com.bing.lib.thread;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/3
 * 描述：
 */
public class ThreadTest1 {
    //产品
    static class ProductObject {
        //volatile 线程操作，变量可见
        public volatile static String value;
    }

    //生产者线程
    public static class Producter extends Thread {
        Object lock;
        public Producter(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {

                //不断生产产品
                while (true) {
                    synchronized (lock){  //代码块加锁
                        //产品还没有被消费，就等待
                        if(ProductObject.value!=null){
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //产品已经消费完成，生产新的产品
                        ProductObject.value = "NO:" + System.currentTimeMillis();
                        System.out.println("生产：" + ProductObject.value);
                        lock.notify(); //通知消费者消费，已经生产完成
                }
            }
        }
    }

    //消费者线程
    public static class Consumer extends Thread {
        Object lock;
        public Consumer(Object lock) {
            this.lock = lock;
        }
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {  //代码块加锁
                    //发现没有产品可以消费  就等待
                    if (ProductObject.value == null) {
                        //阻塞
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                        System.out.println("消费：" + ProductObject.value);
                        ProductObject.value = null;
                        lock.notify(); //通知生产者继续生产 消费完成
                }
            }
        }

    }

    public static void main(String[] args) {
        Object lock = new Object();
        new Producter(lock).start();
        new Consumer(lock).start();
    }
}
