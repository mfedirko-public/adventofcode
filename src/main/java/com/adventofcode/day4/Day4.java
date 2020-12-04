package com.adventofcode.day4;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Day4 {
    public static void main(String[] args) throws IOException {
        InputStream file = Day4.class.getResourceAsStream("/day4.txt");
        PassportReader reader = new PassportReader(file);
        int validCount = 0;
        int validCountPt2 = 0;
        Passport passport;
        do {
            passport = reader.next();
            if (passport != null && passport.isValid()) {
                validCount++;
            }
            if (passport != null && passport.isValidStrict()) {
                validCountPt2++;
            }
        } while (passport != null);
        System.out.printf("Valid passport count: %d\nValid passport count (pt2): %d\n", validCount, validCountPt2);

    }
}
