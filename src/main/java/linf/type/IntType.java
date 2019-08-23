package linf.type;

import linf.error.semantic.SemanticError;
import linf.utils.Environment;

import java.util.ArrayList;
import java.util.List;

public class IntType extends LinfType {
    @Override
    public List<SemanticError> checkSemantics(Environment env) {
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
        return "int";
    }
}
