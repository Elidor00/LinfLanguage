package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

class ComplexExtdIntValue extends ComplexExtdValue {
    ComplexExtdIntValue(String value) {
        setValue(value);
        setType(new ComplexExtdIntType());
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        return getType();
    }
}
