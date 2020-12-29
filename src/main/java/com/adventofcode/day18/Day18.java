package com.adventofcode.day18;

import com.adventofcode.FileStreamSupport;

public class Day18 {
    public static void main(String[] args) {
        long pt1 = FileStreamSupport.toStream(Day18.class.getResourceAsStream("/day18.txt"))
                .map(l -> new Expression(l).eval())
                .reduce(0L, Long::sum);
        System.out.printf("Pt1: %d\n", pt1);
        long pt2 = FileStreamSupport.toStream(Day18.class.getResourceAsStream("/day18.txt"))
                .map(l -> new Expression(l, true).eval())
                .reduce(0L, Long::sum);
        System.out.printf("Pt2: %d\n", pt2);
    }

}
