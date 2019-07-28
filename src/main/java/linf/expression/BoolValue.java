package linf.expression;

import linf.type.BoolType;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.error.semantic.SemanticError;

import java.util.ArrayList;

public class BoolValue extends LinfValue {
    public BoolValue(String value) {
        setValue(value);
        setType(new BoolType());
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public LinfType checkType() {
        return getType();
    }

    @Override
    public String codeGen() {
        return null;
    }
}
