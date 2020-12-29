package com.adventofcode.day18;

import java.util.HashMap;
import java.util.Map;

enum Operators implements Operator {
    ADDITION {
        @Override
        public long eval(long a, long b) {
            return  a + b;
        }
    },
    MULTIPLICATION {
        @Override
        public long eval(long a, long b) {
            return a * b;
        }
    };

    private static Map<Character, Operators> LOOKUP = new HashMap<>();
    static {
        LOOKUP.put('+', ADDITION);
        LOOKUP.put('*', MULTIPLICATION);
    }

    static Operators fromSymbol(char symbol) {
        Operators op = LOOKUP.get(symbol);
        if (op == null) System.out.println("Invalid operator: " + symbol);
        return op;
    }
}
