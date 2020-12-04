package com.adventofcode.day2;

import com.adventofcode.FileStreamSupport;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    private static final Pattern LINE_PATTERN = Pattern.compile("^(?<min>\\d+)-(?<max>\\d+)\\s+(?<ltr>[a-z]):\\s+(?<pwd>[a-z]+).*");

    public static void main(String[] args) throws IOException {
        InputStream file = Day2.class.getResourceAsStream("/day2.txt");
        AtomicInteger validPt1 = new AtomicInteger();
        AtomicInteger validPt2 = new AtomicInteger();
        FileStreamSupport.toStream(file)
                .map(line -> {
                    char letter;
                    int minOccurs, maxOccurs;
                    String password;
                    Matcher matcher = LINE_PATTERN.matcher(line);
                    if (matcher.matches()) {
                        letter = matcher.group("ltr").toCharArray()[0];
                        minOccurs = Integer.parseInt(matcher.group("min"));
                        maxOccurs = Integer.parseInt(matcher.group("max"));
                        password = matcher.group("pwd");
                        return new RuleWithPassword(letter, minOccurs, maxOccurs, password);
                    }
                    throw new IllegalStateException("Invalid entry!  " + line);
                })
                .forEach(rule -> {
                    if (rule.isValidPasswordPt1()) validPt1.incrementAndGet();
                    if (rule.isValidPasswordPt2()) validPt2.incrementAndGet();
                });

        System.out.printf("Valid count part 1: %d\nValid count part 2: %d\n", validPt1.get(), validPt2.get());
    }
}
