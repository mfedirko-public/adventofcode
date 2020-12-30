package com.adventofcode.day19;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    public static void main(String[] args) {
        InputStream file = Day19.class.getResourceAsStream("/day19.txt");
        AtomicInteger sectionRef = new AtomicInteger(0);
        RuleSet ruleSet = new RuleSet();
        RuleSet ruleSetPt2 = new RuleSet(true);
        AtomicInteger validCount = new AtomicInteger(0);
        AtomicInteger validCountPt2 = new AtomicInteger(0);
        FileStreamSupport.toStream(file, "\n\n")
                .forEach(l -> {
                    int section = sectionRef.getAndIncrement();
                    if (section == 0) {
                        Arrays.stream(l.split("\n"))
                            .forEach(r -> {
                                String[] tokens = r.split(": ");
                                int key = Integer.parseInt(tokens[0]);
                                String rule = tokens[1];
                                ruleSet.addRule(key, rule);
                                ruleSetPt2.addRule(key, rule);
                            });
                    } else {
                        Arrays.stream(l.split("\n"))
                            .forEach(in -> {
                                if (ruleSet.validate(in)) validCount.incrementAndGet();
                                if (ruleSetPt2.validate(in)) validCountPt2.incrementAndGet();
                            });
                    }
                });
        System.out.printf("Pt1: %d\n", validCount.get());
        System.out.printf("Pt2: %d\n", validCountPt2.get());
    }

    static class RuleSet {
        private Map<Integer, Supplier<String>> lazyRegexes = new HashMap<>();
        private Pattern cachedRegex;
        private final boolean part2;
        RuleSet() {
            this(false);
        }
        RuleSet(boolean part2) {
            this.part2 = part2;
        }

        boolean validate(String input) {
            if (cachedRegex == null) {
                cachedRegex = Pattern.compile("^" + lazyRegexes.get(0).get() + "$");
            }
            Matcher matcher = cachedRegex.matcher(input);
            return matcher.matches();
        }

        void addRule(int key, String rule) {
            if (key == 8 && part2) {
                lazyRegexes.put(key, new InfiniteLoopAwareProxy(() -> parseAsRegex("42 | 42 8")));
            } else if (key == 11 && part2) {
                lazyRegexes.put(key, new InfiniteLoopAwareProxy(() -> parseAsRegex("42 31 | 42 11 31")));
            } else {
                lazyRegexes.put(key, () -> parseAsRegex(rule));
            }
        }

        String parseAsRegex(String rule) {
            StringBuilder regexBuilder = new StringBuilder();
            StringBuilder currentNum = new StringBuilder();
            PrimitiveIterator.OfInt it = rule.chars().iterator();
            boolean quotesOpen = false;
            while (it.hasNext()) {
                char c = (char)it.nextInt();
                if (c == '"') {
                    quotesOpen = !quotesOpen;
                } else if (quotesOpen) {
                    regexBuilder.append(c);
                } else {
                    if (c >= '0' && c <= '9') {
                        currentNum.append(c);
                    } else if (c == '|') {
                        regexBuilder.append('|');
                    }
                }

                if (!(c >= '0' && c <= '9') || !it.hasNext()) {
                    if (currentNum.length() > 0) {
                        int index = Integer.parseInt(currentNum.toString());
                        regexBuilder.append(lazyRegexes.get(index).get());
                        currentNum.setLength(0);
                    }
                }
            }
            regexBuilder.insert(0, "(").append(")");
            return regexBuilder.toString();
        }
    }

    static class InfiniteLoopAwareProxy implements Supplier<String> {
        private int callCount = 0;
        private final int maxCalls;
        private final Supplier<String> delegate;
        private InfiniteLoopAwareProxy(final Supplier<String> supplier) {
            this.delegate = supplier;
            this.maxCalls = 10;
        }

        @Override
        public String get() {
            if (callCount++ < maxCalls)
                return delegate.get();
            return ""; // break the infinite loop
        }
    }
}
