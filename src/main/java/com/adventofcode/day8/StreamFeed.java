package com.adventofcode.day8;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class StreamFeed {
    private final List<String> originalFeed = new ArrayList<>();
    private List<String> feed;
    private Set<Integer> history = new LinkedHashSet<>();

    private int pos = -1;
    public StreamFeed(Stream<String> feed) {
        feed.forEach(this.originalFeed::add);
        this.feed = new ArrayList<>(originalFeed);
    }

    public String consume() throws InfiniteLoopException {
        if (history.contains(this.pos)) {
            throw new InfiniteLoopException();
        }
        String cmd = this.feed.get(pos);
        this.history.add(this.pos);
        return cmd;
    }

    public void moveFeed(int offset) {
        this.pos += offset;
    }

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
