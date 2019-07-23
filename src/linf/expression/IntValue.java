package linf.expression;

import linf.type.IntType;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.SemanticError;

import java.util.ArrayList;

public class IntValue extends Value {
    public IntValue(String value) {
        setValue(value);
        setType(new IntType());
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
