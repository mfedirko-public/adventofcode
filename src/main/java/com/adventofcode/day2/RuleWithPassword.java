package com.adventofcode.day2;

public class RuleWithPassword extends Rule {
    public final String password;
    public RuleWithPassword(char letter, int minOccurs, int maxOccurs, String password) {
        super(letter, minOccurs, maxOccurs);
        this.password = password;
    }


    public boolean isValidPasswordPt1() {
        return super.isSatisfiedPt1(this.password);
    }
    public boolean isValidPasswordPt2() {
        return super.isSatisfiedPt2(this.password);
    }
}
