package com.adventofcode.day8;

public interface CommandFeed {
    String consume();
    void moveFeed(int offset);
    boolean hasNext();
}
