package com.adventofcode.day3;

import java.io.IOException;
import java.io.InputStream;

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

    private static int getTreeCount(Slope slope) throws IOException {
        InputStream map = Day3.class.getResourceAsStream("/day3.txt");
        Tobbogan tobbogan = new Tobbogan(map, slope);
        int treeCount = 0;
        Tobbogan.Terrain terrain;
        do {
            terrain = tobbogan.next();
            if (terrain == Tobbogan.Terrain.TREE) {
                treeCount++;
            }
        } while (terrain != null);
        return treeCount;
    }


}
