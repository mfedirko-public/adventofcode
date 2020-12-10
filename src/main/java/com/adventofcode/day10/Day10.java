package com.adventofcode.day10;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) {
        InputStream file = Day10.class.getResourceAsStream("/day10.txt");
        Map<Integer, Integer> diffsCount = new HashMap<>();
        AtomicLong configurations = new AtomicLong(1);
        AtomicInteger onesStreak = new AtomicInteger();
        FileStreamSupport.toStream(file, 200)
                .mapToInt(Integer::parseInt)
                .sorted()
                .reduce(0, (prev, next) -> {
                    int diff = next - prev;
                    int existingCount = diffsCount.getOrDefault(diff, 0);
                    diffsCount.put(diff, existingCount + 1);
                    if (diff == 1) {
                        onesStreak.incrementAndGet();
                    } else {
                        int streak = onesStreak.get();
                        configurations.set(configurations.get() * getConfigurationsCount(streak));
                        onesStreak.set(0);
                    }
                    return next;
                });
        int streak = onesStreak.get();
        configurations.set(configurations.get() * getConfigurationsCount(streak));
        diffsCount.put(3, diffsCount.getOrDefault(3, 0) + 1);
        System.out.printf("Pt 1: %d\n",diffsCount.getOrDefault(3, 0) * diffsCount.getOrDefault(1, 0));
        System.out.printf("Pt 2: %d\n", configurations.get());
    }

    // https://en.wikipedia.org/wiki/Lazy_caterer%27s_sequence
    private static long getConfigurationsCount(int streak) {
        if (streak == 0) return 1;
        streak = streak - 1;
        return (streak * streak + streak + 2) / 2;
    }


}
