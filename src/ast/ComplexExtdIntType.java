package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ComplexExtdIntType extends ComplexExtdType {
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public ComplexExtdType checkType() {
        return null;
    }

    @Override
    public String toString() {
        return "int";
    }
}
