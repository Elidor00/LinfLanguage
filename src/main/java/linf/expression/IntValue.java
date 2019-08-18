package linf.expression;

import linf.error.semantic.SemanticError;
import linf.type.IntType;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;

public class IntValue extends LinfValue {
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

    @Override
    public String codeGen() {
        return "li $a0 " + this.value + " \n";
    }
}
