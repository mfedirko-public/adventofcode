package com.adventofcode.day5;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoardingPass {
    public final int row;
    public final int column;
    public BoardingPass(String encoded) {
        String rowEncoded = encoded.substring(0, 7);
        String rowBinary = rowEncoded.chars()
                            .map(c -> c == 'B' ? 1 : 0)
                            .mapToObj(c -> "" + c)
                            .collect(Collectors.joining());
        this.row = Integer.parseInt(rowBinary, 2);
        String colEncoded = encoded.substring(7);
        String colBinary = colEncoded.chars()
                .map(c -> c == 'R' ? 1 : 0)
                .mapToObj(c -> "" + c)
                .collect(Collectors.joining());
        this.column = Integer.parseInt(colBinary, 2);
    }

    public int getSeatID() {
        return this.row * 8 + this.column;
    }
}
