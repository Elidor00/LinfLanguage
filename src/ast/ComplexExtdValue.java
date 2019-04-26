package ast;

import java.util.ArrayList;

public class ComplexExtdValue extends ComplexExtdTerm {
    private boolean isID = false;
    private String value;

    ComplexExtdValue(String value) {
        this.value = value;
    }

    public void setID(boolean ID) {
        isID = ID;
    }

    public String getIDName() {
        return value;
    }

    public ComplexExtdType computeType() {
        try {
            ComplexExtdIntType res = new ComplexExtdIntType();
            res.setRef(isID);
            Integer.parseInt(value);
            return res;
        } catch (NumberFormatException e) {
            ComplexExtdType res = new ComplexExtdBoolType();
            res.setRef(isID);
            return res;
        }
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        if (!isID) {
            return this.computeType();
        } else {
            return env.getStEntry(value).getType();
        }
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }
}
