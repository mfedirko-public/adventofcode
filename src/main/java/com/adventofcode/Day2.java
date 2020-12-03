package com.adventofcode;

import java.io.IOException;
import java.io.InputStream;

public class Day2 {
    public static void main(String[] args) throws IOException {
        InputStream file = Day2.class.getResourceAsStream("/day2.txt");
        RuleReader reader = new RuleReader(file);
        int validPt1 = 0;
        int validPt2 = 0;
        RuleWithPassword rwp;
        do {
            rwp = reader.readNext();
            if (rwp.isValidPasswordPt1()) {
                validPt1++;
            }
            if (rwp.isValidPasswordPt2()) {
                validPt2++;
            }
        } while (rwp != RuleReader.END_OF_FILE);
        System.out.printf("Valid count part 1: %d\nValid count part 2: %d\n", validPt1, validPt2);
    }
}
