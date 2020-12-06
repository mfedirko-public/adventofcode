package com.adventofcode.day6;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnswerSet {
    private final Map<Character, Long> answerCounts;
    private final int groupMemberCount;
    public AnswerSet(String groupAnswers) {
        final AtomicInteger groupMemberCount = new AtomicInteger();
        answerCounts = groupAnswers.chars()
                .filter(i -> {
                    boolean filter = i >= 'a' && i <= 'z';
                    if ((char)i == '\n') groupMemberCount.incrementAndGet();
                    return filter;
                })
                .mapToObj(i -> (char)i)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        this.groupMemberCount =  groupMemberCount.get() + 1;
    }

    public Collection<Character> getDistinctAnswers() {
        return answerCounts.keySet();
    }

    public Collection<Character> getCommonAnswers() {
        return getDistinctAnswers().stream()
                .filter(c -> this.answerCounts.get(c) == this.groupMemberCount)
                .collect(Collectors.toList());
    }
}
