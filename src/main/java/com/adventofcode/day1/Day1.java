package com.adventofcode.day1;


import com.adventofcode.FileStreamSupport;

import java.io.IOException;
import java.io.InputStream;


public class Day1 {
    public static void main(String[] args) throws IOException {
        InputStream file = Day1.class.getResourceAsStream("/day1.txt");
        Sums sums = new Sums(2020);
        FileStreamSupport.toStream(file)
                .mapToInt(Integer::parseInt)
                .forEach(sums::add);
        int[] twoSum = sums.getTwoSum();
        int[] threeSum = sums.getThreeSum();
        System.out.printf("Solution pt1: %d\nSolution pt2: %d\n",
                twoSum[0] * twoSum[1],
                threeSum[0] * threeSum[1] * threeSum[2]);
    }

}
