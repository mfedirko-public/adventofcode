package com.adventofcode.day7;

import java.util.HashSet;
import java.util.Set;

public class Bag {
    public final String color;
    public final int count;

    public Bag(String color) {
        this.color = color;
        this.count = 0;
    }
    public Bag(String color, int count) {
        this.color = color;
        this.count = count;
    }

}
