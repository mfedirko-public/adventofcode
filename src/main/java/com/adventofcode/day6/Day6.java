package com.adventofcode.day6;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

public class Day6 {
    public static void main(String[] args) {
        InputStream file = Day6.class.getResourceAsStream("/day6.txt");
        AtomicLong distinctAnswerCount = new AtomicLong();
        AtomicLong commonAnswerCount = new AtomicLong();
        FileStreamSupport.toStream(file, "\n\n")
            .map(AnswerSet::new)
            .forEach(as -> {
                distinctAnswerCount.addAndGet(as.getDistinctAnswers().size());
                commonAnswerCount.addAndGet(as.getCommonAnswers().size());
            });
        System.out.printf("Distinct answers: %d\nCommon Answers: %d\n",
                distinctAnswerCount.get(), commonAnswerCount.get());
    }
}
