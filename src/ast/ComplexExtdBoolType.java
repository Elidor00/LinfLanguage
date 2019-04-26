package ast;

import java.util.ArrayList;

public class ComplexExtdBoolType extends ComplexExtdType {
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "bool";
    }
}
