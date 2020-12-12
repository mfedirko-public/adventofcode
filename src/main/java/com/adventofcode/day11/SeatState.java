package com.adventofcode.day11;

import java.util.HashMap;
import java.util.Map;

public enum SeatState {
    EMPTY('L'),
    FLOOR('.'),
    OCCUPIED('#');

    private static final Map<Character, SeatState> map = new HashMap<>();
    static {
        map.put('L', EMPTY);
        map.put('.', FLOOR);
        map.put('#', OCCUPIED);
    }

    private final char symbol;
    SeatState(char symbol) {
        this.symbol = symbol;
    }
    public static SeatState fromSymbol(char symbol) {
        return map.get(symbol);
    }
    public char getSymbol() {
        return symbol;
    }
}
