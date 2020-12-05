package com.adventofcode.day5;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Day5 {
    public static void main(String[] args) {
        InputStream file = Day5.class.getResourceAsStream("/day5.txt");
        AtomicInteger lastSeatID = new AtomicInteger(-1);
        AtomicBoolean found = new AtomicBoolean();
        AtomicInteger missingSeatID = new AtomicInteger(-1);
        FileStreamSupport.toStream(file, 5000)
                .map(line -> new BoardingPass(line).getSeatID())
                .sorted()
                .reduce(0,
                    (sum, id) -> {
                       if (!found.get() && lastSeatID.get() > -1) {
                            if (id - lastSeatID.get() > 1) {
                                missingSeatID.set(id - 1);
                                found.set(true);
                            }
                       }
                       lastSeatID.set(id);
                       return lastSeatID.get();
                    });
        System.out.printf("Max Seat ID: %d\nMissing Seat ID: %d\n", lastSeatID.get(), missingSeatID.get());
    }
}
