package linf.utils;

import linf.type.LinfType;

import java.util.Objects;

public class STentry {

    private final int nestingLevel;
    private final int offset;
    private final LinfType type;

    public STentry(int n, int o, LinfType t) {
        nestingLevel = n;
        offset = o;
        type = t;
    }

    public LinfType getType() {
        return type;
    }

    public int getNestinglevel() {
        return nestingLevel;
    }

    public int getOffset() {
        return offset;
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

