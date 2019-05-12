package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ComplexExtdBoolValue extends ComplexExtdValue {
    ComplexExtdBoolValue(String value) {
        setValue(value);
        setType(new ComplexExtdBoolType());
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public ComplexExtdType checkType() {
        return getType();
    }
}
