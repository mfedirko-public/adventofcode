package com.adventofcode.day15;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day15 {
    public static void main(String[] args) {
        InputStream file = Day15.class.getResourceAsStream("/day15.txt");
        AtomicInteger lastRef = new AtomicInteger(-1);
        Map<Integer, Integer> memory = new HashMap<>();
        AtomicInteger sizeRef = new AtomicInteger(1);
        FileStreamSupport.toStream(file)
                .forEach(l ->
                        Arrays.stream(l.split(","))
                                .forEach(n -> {
                                    int last = lastRef.get();
                                    if (last != -1) {
                                        memory.put(last, sizeRef.getAndIncrement() - 1);
                                    }
                                    lastRef.set(Integer.parseInt(n));
                                })
                );
        while (sizeRef.get() < 2020) {
            computeNext(sizeRef, lastRef, memory);
        }
        System.out.printf("Pt1: %d\n", lastRef.get());
        while (sizeRef.get() < 30_000_000) {
            computeNext(sizeRef, lastRef, memory);
        }
        System.out.printf("Pt2: %d\n", lastRef.get());
    }

    private static int computeNext(AtomicInteger size, AtomicInteger lastRef, Map<Integer, Integer> memory) {
        int last = lastRef.get();
        int next;
        if (memory.containsKey(last)) {
            next = size.get() - 1 - memory.get(last);
        } else {
            next = 0;
        }
        memory.put(last, size.getAndIncrement() - 1);
        lastRef.set(next);
        return next;
    }
}
