package com.adventofcode.day8;

import java.util.concurrent.atomic.AtomicInteger;

public interface Command {
    void execute(int input, StreamFeed feed, AtomicInteger accumulator);
}
