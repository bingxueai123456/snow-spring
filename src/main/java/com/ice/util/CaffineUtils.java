package com.ice.util;

import java.util.concurrent.TimeUnit;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffineUtils {
    Cache<Integer, TreeMap<Integer, byte[]>> activeTriggerResponseCache = Caffeine.newBuilder().expireAfterAccess(5, TimeUnit.SECONDS).build();
    public static void main(String[] args) {
        CaffineUtils caffineUtils = new CaffineUtils();
        TreeMap<Integer, byte[]> treeMap = caffineUtils.activeTriggerResponseCache.get(1, key -> new TreeMap<>());
        treeMap.put(1, new byte[] { 0x01, 0x02, 0x03 });
        treeMap.put(2, new byte[] { 0x04, 0x05, 0x06 });
        System.out.println(treeMap);
        System.out.println(caffineUtils.activeTriggerResponseCache.getIfPresent(1));
        System.out.println(caffineUtils.activeTriggerResponseCache.getIfPresent(2));
        System.out.println(caffineUtils.activeTriggerResponseCache.getIfPresent(3));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(caffineUtils.activeTriggerResponseCache.getIfPresent(1));
        System.out.println(caffineUtils.activeTriggerResponseCache.getIfPresent(2));
        System.out.println(caffineUtils.activeTriggerResponseCache.getIfPresent(3));
    }

}
    