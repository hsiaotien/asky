package com.ceres.queue;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {

    public static void main(String[] args) {
//        HashMap<String, String> map = new HashMap<>();
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");
        map.put("5", "e");
        map.put("6", "f");
        map.put("7", "g");
        map.put("8", "h");
        new Thread1(map).start();
        new Thread2(map).start();

    }

}

class Thread1 extends Thread {

    private final Map map;

    Thread1(Map map) {
        this.map = map;
    }

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.remove("6");
    }
}

class Thread2 extends Thread {

    private final Map map;

    Thread2(Map map) {
        this.map = map;
    }

    @Override
    public void run() {
        super.run();
        Set set = map.keySet();
        for (Object next : set) {
            System.out.println(next + ":" + map.get(next));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}