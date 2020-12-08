package com.adventofcode.day7;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;

public class Day7 {
    public static void main(String[] args) {
        InputStream file = Day7.class.getResourceAsStream("/day7.txt");
        BagRegistry bagRegistry = new BagRegistry();
        FileStreamSupport.toStream(file)
                .forEach(bagRegistry::register);
        long parentCount = bagRegistry.getCumulativeParentSet("shiny gold").size();
        long childCount = bagRegistry.getCumulativeChildCount("shiny gold");
        System.out.printf("Parent count: %d\nChild count: %d\n", parentCount, childCount);
    }
}
