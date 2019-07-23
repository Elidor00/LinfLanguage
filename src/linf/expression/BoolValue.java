package linf.expression;

import linf.type.BoolType;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.SemanticError;

import java.util.ArrayList;

public class BoolValue extends Value {
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
}
