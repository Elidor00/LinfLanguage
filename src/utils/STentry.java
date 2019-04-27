package utils;

import ast.ComplexExtdType;

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
}

