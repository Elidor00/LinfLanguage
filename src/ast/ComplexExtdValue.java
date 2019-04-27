package ast;

import ast.ComplexExtdType;
import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public abstract class ComplexExtdValue implements Node {
    private ComplexExtdType type;
    protected String value;

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(ComplexExtdType type) {
        this.type = type;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        return type;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return value;
    }
}
