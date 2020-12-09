package com.adventofcode.day8;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day8 {
    public static void main(String[] args) {
        InputStream file = Day8.class.getResourceAsStream("/day8.txt");
        StreamFeed feed = new StreamFeed(FileStreamSupport.toStream(file));
        AtomicInteger accumulator = new AtomicInteger();
        Integer endingValue = null;
        Iterator<Integer> historyIterator = null;
        while (endingValue == null) {
            try {
                endingValue = runFeed(feed, accumulator);
            } catch (InfiniteLoopException ex) {
                System.out.printf("Infinite loop encountered.\nAccumulator: %d\n", accumulator.get());
                if (historyIterator == null) historyIterator = feed.getHistory().iterator();
                boolean updated = false;
                while (!updated) {
                    updated = feed.changeInstruction(historyIterator.next());
                }
            }
        }
        System.out.printf("Ending value of Accumulator: %d\n", endingValue);
    }

    private static Integer runFeed(StreamFeed feed, AtomicInteger accumulator) throws InfiniteLoopException {
        accumulator.set(0);
        while (feed.hasNext()) {
            feed.moveFeed(1);
            Commands.execute(feed, accumulator);
        }
        return accumulator.get();
    }

}
