package com.adventofcode.day11;

import com.adventofcode.FileStreamSupport;
import java.io.InputStream;

public class Day11 {
    public static void main(String[] args) {
        InputStream file = Day11.class.getResourceAsStream("/day11.txt");
        char[][] symbols;
        symbols = FileStreamSupport.toStream(file, 90)
                .map(String::toCharArray)
                .toArray(char[][]::new);
        part1(symbols);
        part2(symbols);
    }

    private static void part1(char[][] symbols) {
        SeatGrid grid = new SeatGrid(symbols);
        do {
            grid = grid.nextGrid();
        } while (grid.hasChanges());
        System.out.printf("Occupied seats at steady state: %d\n", grid.totalOccupiedSeats());
    }
    private static void part2(char[][] symbols) {
        int iterations = 0;
        SeatGrid grid = new SeatGrid(symbols, true);
        do {
            grid = grid.nextGrid();
            iterations++;
        } while (grid.hasChanges());
        System.out.printf("Occupied seats at steady state (pt2): %d\n", grid.totalOccupiedSeats());
    }
}
