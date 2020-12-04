package com.adventofcode.day3;

import com.adventofcode.FileStreamSupport;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class Day3 {
    public static void main(String[] args) throws IOException {
        System.out.printf("Tree count pt1: %d\n", getTreeCount(new Slope(1, 3)));

        long cnt1 = getTreeCount(new Slope(1, 1));
        long cnt2 = getTreeCount(new Slope(1, 3));
        long cnt3 = getTreeCount(new Slope(1, 5));
        long cnt4 = getTreeCount(new Slope(1, 7));
        long cnt5 = getTreeCount(new Slope(2, 1));
        long multiple = cnt1 * cnt2 * cnt3 * cnt4 * cnt5;
        System.out.printf("Tree count pt2: %d\n", multiple);
    }

    private static long getTreeCount(Slope slope) throws IOException {
        InputStream map = Day3.class.getResourceAsStream("/day3.txt");
        AtomicInteger x = new AtomicInteger(0);
        AtomicInteger y = new AtomicInteger(0); // start at 0
        return FileStreamSupport.toStream(map)
                .map(String::toCharArray)
                .filter(chs -> {
                    int indexY = y.getAndIncrement();
                    if (indexY % slope.dy != 0) return false;
                    int indexX = x.getAndAdd(slope.dx) % chs.length;
                    Terrain terrain = Terrain.fromSymbol(chs[indexX]);
                    return terrain == Terrain.TREE;
                })
                .count();

    }


}
