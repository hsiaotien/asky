package com.ceres.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueExample {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedElement> queue = new DelayQueue<>();
        DelayedElement element1 = new DelayedElement(10000);
        DelayedElement element2 = new DelayedElement(0);
        DelayedElement element3 = new DelayedElement(4000);
        queue.put(element1);
        queue.put(element2);
        queue.put(element3);
        DelayedElement e = queue.take();
        System.out.println("e1:" + e.delayTime);
        DelayedElement e2 = queue.take();
        System.out.println("e2:" + e2.delayTime);
        DelayedElement e3 = queue.take();
        System.out.println("e3:" + e3.delayTime);
    }
}

class DelayedElement implements Delayed {
    long delayTime;
    long tamp;

    DelayedElement(long delay) {
        delayTime = delay;
        tamp = delay + System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return tamp - System.currentTimeMillis();
//        return -1;
    }

    @Override
    public int compareTo(Delayed o) {
        return tamp - ((DelayedElement) o).tamp > 0 ? 1 : -1;
    }
}