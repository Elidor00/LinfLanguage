package linf.utils;

import linf.type.LinfType;

public class STentry {

    private final int nestingLevel;
    private final int offset;
    private final LinfType type;
    private final String name;

    public STentry(int n, int o, LinfType t, String name) {
        assert t != null;
        assert name != null;
        nestingLevel = n;
        offset = o;
        type = t;
        this.name = name;
    }

    public String getName() {
        return name;
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

        if (nestingLevel != sTentry.nestingLevel) return false;
        if (offset != sTentry.offset) return false;
        return type.equals(sTentry.type) && name.equals(sTentry.name);
    }

    @Override
    public int hashCode() {
        int result = nestingLevel;
        result = 31 * result + offset;
        result = 31 * result + type.hashCode();
        return result;
    }
}

