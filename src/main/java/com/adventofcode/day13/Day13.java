package com.adventofcode.day13;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Day13 {
    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        InputStream file = Day13.class.getResourceAsStream("/day13.txt");
        AtomicLong timestamp = new AtomicLong(-1);
        AtomicInteger optimalRoute = new AtomicInteger();
        AtomicLong waitTime = new AtomicLong();
        FileStreamSupport.toStream(file)
                .filter(line -> {
                    if (timestamp.get() <= 0) {
                        timestamp.set(Long.parseLong(line));
                        return false;
                    }
                    return true;
                })
                .forEach(line -> {
                    int optimal = Arrays.stream(line.split(","))
                            .filter(c -> !c.equals("x"))
                            .map(Integer::parseInt)
                            .reduce((i1, i2) -> {
                                long time = timestamp.get();
                                long waitTime1 = i1 - time % i1;
                                long waitTime2 = i2 - time % i2;
                                if (waitTime1 > waitTime2) return i2;
                                else return i1;
                            }).get();
                    optimalRoute.set(optimal);
                    waitTime.set(optimal - timestamp.get() % optimal);
                });

        System.out.printf("Pt1: %d\n", optimalRoute.get() * waitTime.get());
    }
    private static void part2() {
        InputStream file = Day13.class.getResourceAsStream("/day13.txt");
        AtomicInteger currentRem = new AtomicInteger(0);
        List<NumSet> numSets = new ArrayList<>();
        FileStreamSupport.toStream(file)
                .skip(1)
                .forEach(line -> {
                   Arrays.stream(line.split(","))
                            .forEach(str -> {
                                if (!str.equals("x")) {
                                    NumSet ns = new NumSet(Integer.parseInt(str), currentRem.get());
                                    numSets.add(ns);
                                }
                                currentRem.incrementAndGet();
                            });

                });
        numSets.sort(Comparator.comparing((NumSet ns) -> ns.mod).reversed());
        long n = find(numSets);
        System.out.printf("Pt2: %d\n", n);
    }

    private static long find(List<NumSet> ns) {
        int i = 0;
        long num = ns.get(0).rem;
        long add = ns.get(0).mod;
        while (i + 1 < ns.size()) {
            long mod1 = ns.get(i).mod;
            long rem1 = ns.get(i).rem;
            long mod2 = ns.get(i+1).mod;
            long rem2 = ns.get(i+1).rem;

            while (true) {
                if (num % mod1 == rem1 && num % mod2 == rem2) {
                    add *= mod2;
                    break;
                }
                num += add;
            }
            i++;
        }
        return num;
    }

    private static class NumSet {
        final int mod;
        final int rem;

        public NumSet(int mod, int rem) {
            this.mod = mod;
            if (rem > mod) rem = rem % mod;
            this.rem = (mod - rem) % mod;
        }
    }
}
