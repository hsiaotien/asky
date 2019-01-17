package com.ceres.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1024);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

class Producer implements Runnable {
    private BlockingQueue<String> queue;

    Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("1");
            Thread.sleep(1000);
            queue.put("2");
            Thread.sleep(5000);
            queue.put("3");
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue queue;

    Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    public void run() {
        try {

            System.out.println(String.valueOf(atomicInteger.incrementAndGet())+":"+ queue.take());
            System.out.println(String.valueOf(atomicInteger.incrementAndGet())+":"+ queue.take());
            System.out.println(String.valueOf(atomicInteger.incrementAndGet())+":"+ queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}