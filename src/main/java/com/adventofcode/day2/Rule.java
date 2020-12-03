package com.adventofcode.day2;

import java.util.Objects;

public class Rule {
    public final char letter;
    public final int minOccurs;
    public final int maxOccurs;

    public Rule(char letter, int minOccurs, int maxOccurs) {
        this.letter = letter;
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }

    public boolean isSatisfiedPt1(String password) {
        int actualOccurs = 0;
        for (char letter : password.toCharArray()) {
            if (letter == this.letter) {
                actualOccurs++;
            }
            if (actualOccurs > maxOccurs) return false;
        }
        return actualOccurs >= minOccurs && actualOccurs <= maxOccurs;
    }
    public boolean isSatisfiedPt2(String password) {
        boolean firstValid = false;
        boolean secondValid = false;
        if (password.length() >= minOccurs) {
            firstValid = password.charAt(minOccurs - 1) == letter;
        }
        if (password.length() >= maxOccurs) {
            secondValid = password.charAt(maxOccurs - 1) == letter;
        }
        return firstValid ^ secondValid;
    }
}
