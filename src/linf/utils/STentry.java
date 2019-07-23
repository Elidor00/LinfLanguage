package linf.utils;

import linf.type.LinfType;

import java.util.Objects;

public class STentry {

    private int nestingLevel;
    private LinfType type;

    public STentry(int n, LinfType t) {
        nestingLevel = n;
        type = t;
    }

    public LinfType getType() {
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

