package ast;

import java.util.ArrayList;

public class ComplexExtdIntType extends ComplexExtdType {
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "int";
    }
}
