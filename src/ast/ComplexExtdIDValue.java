package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.Objects;

public class ComplexExtdIDValue extends ComplexExtdValue {
    private STentry entry;

    ComplexExtdIDValue(String value) {
        setValue(value);
    }

    STentry getEntry() {
        return entry;
    }

    void setEntry(STentry entry) {
        this.entry = entry;
        this.setType(entry.getType());
    }

    @Override
    public ComplexExtdType checkType() {
        return getType();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();
        entry = env.getStEntry(value);
        if (entry == null) {
            res.add(new SemanticError(value + " is not an identifier in current scope."));
        } else {
            setType(entry.getType());
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexExtdIDValue idValue = (ComplexExtdIDValue) o;
        return Objects.equals(getEntry(), idValue.getEntry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntry());
    }
}
