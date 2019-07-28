package linf.type;

import linf.utils.Environment;
import linf.error.semantic.SemanticError;

import java.util.ArrayList;

public class BoolType extends LinfType {
    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public LinfType checkType() {
        return null;
    }

    @Override
    public String codeGen() {
        return null;
    }

    @Override
    public String toString() {
        return "bool";
    }
}
