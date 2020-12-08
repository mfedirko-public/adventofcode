package com.adventofcode.day7;

import java.util.Objects;

public class BagKey {
    public final String color;
    public final Type type;

    public BagKey(String color, Type type) {
        this.color = color;
        this.type = type;
    }

    public enum Type {
        PARENT,
        CHILD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BagKey bagKey = (BagKey) o;
        return color.equals(bagKey.color) &&
                type == bagKey.type;
    }
    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }
}
