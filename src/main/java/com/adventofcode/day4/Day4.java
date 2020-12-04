package com.adventofcode.day4;

import com.adventofcode.FileStreamSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class Day4 {
    public static void main(String[] args) throws IOException {
        InputStream file = Day4.class.getResourceAsStream("/day4.txt");
        List<Passport> validPassports =
                FileStreamSupport.toStream(file, "\n\n")
                .map(s -> s.split("\\s"))
                .map(s -> Arrays.stream(s).map(e -> e.split(":")).collect(Collectors.toMap(e -> e[0], e -> e[1])))
                .map(Passport::new)
                .filter(Passport::isValid)
                .collect(Collectors.toList());

        int validCountPt1 = validPassports.size();
        int validCountPt2 = (int)validPassports.stream().filter(Passport::isValidStrict).count();
        System.out.printf("Valid passport count: %d\nValid passport count (pt2): %d\n", validCountPt1, validCountPt2);

    }
}
