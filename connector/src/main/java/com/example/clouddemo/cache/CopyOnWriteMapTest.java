package com.example.clouddemo.cache;

public class CopyOnWriteMapTest {
    public static void main(String[] args) {
        CopyOnWriteMap copyOnWriteMap = new CopyOnWriteMap();
        copyOnWriteMap.put("1", null, 1);
        copyOnWriteMap.put("2", null, 2);
        copyOnWriteMap.put("3", null, 4);
        copyOnWriteMap.put("5", null, 5);
        copyOnWriteMap.setScore("1", 3);
        copyOnWriteMap.remove("2");

    }
}
