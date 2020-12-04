package com.adventofcode.day3;

public enum Terrain {
    TREE('#'),
    PLAIN('.');

    private final char symbol;
    Terrain(char symbol) {
        this.symbol = symbol;
    }

    static Terrain fromSymbol(char symbol) {
        for (Terrain val : Terrain.values()) {
            if (val.symbol == symbol) {
                return val;
            }
        }
        return null;
    }

}
