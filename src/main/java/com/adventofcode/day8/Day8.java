package com.adventofcode.day8;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

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
        Commands commands = new Commands();
        while (feed.hasNext()) {
            feed.moveFeed(1);
            commands.execute(feed, accumulator);
        }
        return accumulator.get();
    }

    private static class StreamFeed implements CommandFeed {
        private final List<String> originalFeed = new ArrayList<>();
        private List<String> feed;
        private Set<Integer> history = new LinkedHashSet<>();

        private int pos = -1;
        StreamFeed(Stream<String> feed) {
            feed.forEach(this.originalFeed::add);
            this.feed = new ArrayList<>(originalFeed);
        }

        @Override
        public String consume() throws InfiniteLoopException {
            if (history.contains(this.pos)) {
                throw new InfiniteLoopException();
            }
            String cmd = this.feed.get(pos);
            this.history.add(this.pos);
            return cmd;
        }

        @Override
        public void moveFeed(int offset) {
            this.pos += offset;
        }

        @Override
        public boolean hasNext() {
            return pos + 1 < feed.size();
        }


        public Set<Integer> getHistory() {
            return new LinkedHashSet<>(history);
        }

        public boolean changeInstruction(int pos) {
            if (!feed.get(pos).contains("jmp") && !feed.get(pos).contains("nop")) {
                return false;
            }
            history.clear();
            this.feed = new ArrayList<>(originalFeed);
            String cmd = this.feed.get(pos);
            cmd = cmd.contains("jmp") ? cmd.replace("jmp", "nop") : cmd.replace("nop", "jmp");
            this.feed.set(pos, cmd);
            this.pos = -1;
            return true;
        }
    }
}
