package com.adventofcode.day14;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {
    private static final Pattern MEM_PATTERN = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");
    public static void main(String[] args) {
        InputStream file = Day14.class.getResourceAsStream("/day14.txt");
        Map<Integer, Long> memPt1 = new HashMap<>();
        Map<Long, Long> memPt2 = new HashMap<>();
        AtomicReference<Mask> maskRef = new AtomicReference<>();
        FileStreamSupport.toStream(file).
                forEach(line -> {
                    if (line.startsWith("mask")) {
                        Mask mask = new Mask(line.split("mask = ")[1]);
                        maskRef.set(mask);
                    } else {
                        Matcher matcher = MEM_PATTERN.matcher(line);
                        if (matcher.matches()) {
                            int address = Integer.parseInt(matcher.group(1));
                            long value = Long.parseLong(matcher.group(2));
                            memPt1.put(address, maskRef.get().apply(value));
                            for (long addr : maskRef.get().applyV2(address)) {
                                memPt2.put(addr, value);
                            }
                        }
                    }
                });
        long sum = memPt1.values().stream()
                .mapToLong(l -> l)
                .sum();
        long sum2 = memPt2.values().stream()
                .mapToLong(l -> l)
                .sum();
        System.out.printf("Pt1: %d\n", sum);
        System.out.printf("Pt2: %d\n", sum2);
    }

    private static class Mask {
        private final long mask;
        private final long nonMask;

        Mask(String str) {
            String nonMask = str.replace("1", "0").replaceAll("X", "1");
            this.nonMask = Long.parseLong(nonMask, 2);
            String mask = str.replace("X", "0");
            this.mask = Long.parseLong(mask, 2);
        }

        long apply(long n) {
            long onesMask = mask;
            long zerosMask = ~mask ^ nonMask; // XXXX10010 -> 000001101

            n = n | onesMask;
            n = ~(~n | zerosMask);
            return n;
        }

        List<Long> applyV2(long n) {
            n = n | mask;
            long rem = nonMask;
            long xMask = Long.highestOneBit(rem);
            Set<Long> addrs = new HashSet<>();
            addrs.add(n);
            Set<Long> vals = applyV2(xMask, rem, addrs);
            return new ArrayList<>(vals);
        }

        private Set<Long> applyV2(long xMask, long rem, Set<Long> addrs) {
            Set<Long> next = new HashSet<>(addrs);
            for (long n : addrs) {
                next.add(n ^ xMask);
                next.add(n);
            }
            if (xMask > 0) {
                long other = rem - xMask;
                xMask = Long.highestOneBit(other);
                rem = other;

                addrs.addAll(applyV2(xMask, rem, next));
            }
            return next;
        }
    }
}
