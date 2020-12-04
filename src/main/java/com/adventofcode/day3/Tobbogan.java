package com.adventofcode.day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Tobbogan {
    private final BufferedReader reader;
    private final Slope slope;
    private int x;
    private int y;
    public Tobbogan(InputStream mapStream, Slope slope) throws IOException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        mapStream));
        this.slope = slope;
        reader.readLine(); // starting position (0,0)
    }


    public Terrain next() {
        try {
            String line = null;
            for (int i = 0; i < slope.dy; i++) {
                line = reader.readLine();
                this.y++;
            }
            if (line != null) {
                char[] terrainSymbols = line.toCharArray();
                this.x = (this.x + slope.dx) % terrainSymbols.length;
                char symbol = terrainSymbols[this.x];
                Terrain terrain = Terrain.fromSymbol(symbol);
                System.out.printf("%s at (%d, %d)\n", terrain, x, y);
                return terrain;
            }
            return null;
        } catch (IOException ex) {
            throw new IllegalStateException("Unreadable map!", ex);
        }
    }


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
}
