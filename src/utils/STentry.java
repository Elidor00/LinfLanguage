package utils;

import ast.ComplexExtdType;

import java.util.Objects;

public class STentry {

    private int nestingLevel;
    private ComplexExtdType type;

    public STentry(int n, ComplexExtdType t) {
        nestingLevel = n;
        type = t;
    }

    public ComplexExtdType getType() {
        return type;
    }

    public int getNestinglevel() {
        return nestingLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        STentry sTentry = (STentry) o;
        return nestingLevel == sTentry.nestingLevel &&
                getType().equals(sTentry.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nestingLevel, getType());
    }
}

