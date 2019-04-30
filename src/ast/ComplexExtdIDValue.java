package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ComplexExtdIDValue extends ComplexExtdValue {
    ComplexExtdIDValue(String value) {
        setValue(value);
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        return getType();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        if (!env.containsName(value)) {
            res.add(new SemanticError(value + " is not an identifier in current scope."));
        } else {
            setType(env.getStEntry(value).getType());
        }

        return res;
    }
}
