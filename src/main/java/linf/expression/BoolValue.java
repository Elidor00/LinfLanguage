package linf.expression;

import linf.error.semantic.SemanticError;
import linf.type.BoolType;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;
import java.util.List;

public class BoolValue extends LinfValue {
    public BoolValue(String value) {
        setValue(value);
        setType(new BoolType());
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }

    @Override
    public LinfType checkType() {
        return getType();
    }

    @Override
    public String codeGen() {
        return "li $a0 " + (this.value.equals("true") ? '1' : '0') + "\n";
    }
}
