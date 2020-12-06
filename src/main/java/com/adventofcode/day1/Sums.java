package com.adventofcode.day1;

import java.util.*;

public class Sums {
    private final int requiredSum;
    private int[] twoSum;
    private int[] threeSum;
    private Set<Integer> numbers = new HashSet<>();
    private Set<Long> sums = new HashSet<>();
    private long lastSum = 0L;
    public Sums(int requiredSum) {
        this.requiredSum = requiredSum;
    }

    public void add(int value) {
        if (twoSum == null) {
            boolean found = checkTwoSum(value);
            if (found) twoSum = new int[]{value, requiredSum - value};
        }
        numbers.add(value);

        if (threeSum == null) {
            checkThreeSum(value);
        }
        long sum = lastSum + value;
        sums.add(sum);
    }
    public int[] getTwoSum() {
        return twoSum;
    }
    public int[] getThreeSum() {
        return threeSum;
    }

    private boolean checkTwoSum(int value) {
        return checkTwoSum(value, this.requiredSum);
    }
    private boolean checkTwoSum(int value, int requiredSum) {
        return numbers.contains(requiredSum - value);
    }

    private boolean checkThreeSum(int value) {
        int sum = requiredSum - value;
        for (int num : numbers) {
            if (checkTwoSum(num, sum)) {
                this.threeSum = new int[] {value, num, sum - num};
                return true;
            }
        }
        return false;
    }

}
