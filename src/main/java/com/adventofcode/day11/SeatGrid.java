package com.adventofcode.day11;

import java.awt.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SeatGrid {
    private static final int[][] slopes = {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1},
            {1, 1},
            {-1, -1},
            {1, -1},
            {-1, 1}
    };
    private char[][] grid;
    private char[][] prevGrid;
    private Map<SeatState, Set<Point>> index = new HashMap<>();
    private Set<Point> seatPoints;
    private boolean part2;
    public SeatGrid(char[][] grid) {
        this.grid = grid;
        rebuildIndex();
    }
    public SeatGrid(char[][] grid, boolean part2) {
        this(grid);
        this.part2 = part2;
    }
    public SeatGrid nextGrid() {
        char[][] nextGrid = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
           nextGrid[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        for (int y = 0; y < nextGrid.length; y++) {
            for (int x = 0; x < nextGrid[0].length; x++) {
                if (nextGrid[y][x] == SeatState.FLOOR.symbol) continue;
                if (shouldMove(x, y)) {
                    nextGrid[y][x] = SeatState.EMPTY.symbol;
                } else if (shouldOccupy(x, y)) {
                    nextGrid[y][x] = SeatState.OCCUPIED.symbol;
                }
            }
        }
        this.prevGrid = this.grid;
        this.grid = nextGrid;
        rebuildIndex();
        return this;
    }

    private void rebuildIndex() {
        index.clear();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                SeatState state = SeatState.fromSymbol(grid[y][x]);
                Set<Point> positions = index.getOrDefault(state, new HashSet<>());
                positions.add(new Point(x, y));
                index.put(state, positions);
            }
        }
    }

    public int totalOccupiedSeats() {
        return index.get(SeatState.OCCUPIED).size();
    }

    private Set<Point> getSeatPoints() {
        if (this.seatPoints == null) {
            Set<Point> points = new HashSet<>(index.getOrDefault(SeatState.OCCUPIED, new HashSet<>()));
            Set<Point> points2 = index.getOrDefault(SeatState.EMPTY, new HashSet<>());
            points.addAll(points2);
            this.seatPoints = points;
        }
        return seatPoints;
    }

    private Point getClosestNeighborPoint(int x, int y, int[] slope) {
        if (!index.containsKey(SeatState.EMPTY) && !index.containsKey(SeatState.OCCUPIED)) {
            return null;
        }

        Set<Point> points = getSeatPoints();
        int dy = slope[0];
        int dx = slope[1];
        int mult = 1;
        while (x + dx < grid[0].length && y + dy < grid.length && x + dx >= 0 && y + dy >= 0) {
            Point point = new Point(x + dx, y + dy);
            if (points.contains(point)) {
                return point;
            }
            mult++;
            dy = slope[0] * mult;
            dx = slope[1] * mult;
        }

        return null;
    }


    private boolean shouldOccupy(int x, int y) {
        if (!part2) {
            return shouldOccupyPt1(x, y);
        } else {
            return shouldOccupyPt2(x, y);
        }
    }

    private boolean shouldOccupyPt2(int x, int y) {
        if (grid[y][x] != SeatState.EMPTY.symbol) return false;

        for (int[] slope : slopes) {
            Point neighbor = getClosestNeighborPoint(x, y, slope);
            SeatState state;
            if (neighbor == null) {
                state = SeatState.FLOOR;
            } else {
                state = SeatState.fromSymbol(grid[neighbor.y][neighbor.x]);
            }
            if (state == SeatState.OCCUPIED) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldOccupyPt1(int x, int y) {
        if (grid[y][x] != SeatState.EMPTY.symbol) return false;
        for (int i = Math.max(0, y - 1); i < Math.min(grid.length, y + 2); i++) {
            for (int j = Math.max(0, x - 1); j < Math.min(grid[0].length, x + 2); j++) {
                if (grid[i][j] == SeatState.OCCUPIED.symbol) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean shouldMove(int x, int y) {
        if (!part2) {
            return shouldMovePart1(x, y);
        } else {
            return shouldMovePart2(x, y);
        }
    }

    private boolean shouldMovePart2(int x, int y) {
        if (grid[y][x] != SeatState.OCCUPIED.symbol) return false;
        int occupiedCount = 0;

        for (int[] slope : slopes) {
            Point neighbor = getClosestNeighborPoint(x, y, slope);
            SeatState state;
            if (neighbor == null) {
                state = SeatState.FLOOR;
            } else {
                state = SeatState.fromSymbol(grid[neighbor.y][neighbor.x]);
            }
            if (state == SeatState.OCCUPIED) {
                occupiedCount++;
            }
        }
        return occupiedCount >= 5;
    }
    private boolean shouldMovePart1(int x, int y) {
        if (grid[y][x] != SeatState.OCCUPIED.symbol) return false;
        int occupiedCount = 0;
        for (int i = Math.max(0, y - 1); i < Math.min(grid.length, y + 2); i++) {
            for (int j = Math.max(0, x - 1); j < Math.min(grid[0].length, x + 2); j++) {
                if (i == y && j == x) continue;
                if (grid[i][j] == SeatState.OCCUPIED.symbol) {
                    occupiedCount++;
                }
            }
        }
        return occupiedCount >= 4;
    }

    public boolean hasChanges() {
        return prevGrid == null || !Arrays.deepEquals(prevGrid, grid);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatGrid seatGrid = (SeatGrid) o;
        return Arrays.deepEquals(grid, seatGrid.grid);
    }


    private enum SeatState {
        EMPTY('L'),
        FLOOR('.'),
        OCCUPIED('#');

        private final char symbol;
        SeatState(char symbol) {
            this.symbol = symbol;
        }


        public static SeatState fromSymbol(char symbol) {
            for (SeatState seatState : values()) {
                if (seatState.symbol == symbol) return seatState;
            }
            return null;
        }
    }
}
