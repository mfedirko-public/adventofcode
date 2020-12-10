package com.adventofcode.day9;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.*;

public class Day9 {
    public static void main(String[] args) {
        InputStream file = Day9.class.getResourceAsStream("/day9.txt");
        Set<Long> preamble = new LinkedHashSet<>();
        List<Long> previousNums = new ArrayList<>();
        final int preambleLength = 25;
        FileStreamSupport.toStream(file)
                .map(Long::parseLong)
                .forEach(i -> {
                    if (preamble.size() > preambleLength) {
                        if (preamble.stream().noneMatch(p -> preamble.contains(i - p))) {
                            System.out.printf("Invalid number: %d\n", i);
                            List<Long> sumList = getSublistForSum(i, previousNums);
                            sumList.sort(null);
                            long min = sumList.get(0);
                            long max = sumList.get(sumList.size() - 1);
                            System.out.printf("Part 2: %d\n", min + max);
                            System.exit(0);
                        }
                        Iterator<Long> it = preamble.iterator();
                        it.next();
                        it.remove();
                    }
                    preamble.add(i);
                    previousNums.add(i);
                });
    }

    private static List<Long> getSublistForSum(long i, List<Long> candidates) {
        int startPos = 0;
        int pos;
        while (startPos < candidates.size()) {
            pos = startPos;
            List<Long> currentSubList = new ArrayList<>();
            long currentSum = 0;
            while (currentSum < i) {
                long value = candidates.get(pos);
                currentSubList.add(value);
                currentSum += value;
                pos++;
            }
            if (currentSum == i) {
                return currentSubList;
            }
            startPos++;
        }
        throw new IllegalArgumentException("No continuous list of numbers found which sum to " + i);
    }
}
