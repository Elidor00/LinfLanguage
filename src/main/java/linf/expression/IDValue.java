package linf.expression;

import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundSymbolError;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.Objects;

public class IDValue extends LinfValue {
    private STentry entry;

    public IDValue(String value) {
        setValue(value);
    }

    public STentry getEntry() {
        return entry;
    }

    public void setEntry(STentry entry) {
        this.entry = entry;
        this.setType(entry.getType());
    }

    @Override
    public LinfType checkType() {
        return getType();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();
        entry = env.getStEntry(value);
        if (entry == null) {
            res.add(new UnboundSymbolError(value));
        } else {
            setType(entry.getType());
        }
        return res;
    }

    @Override
    public String codeGen() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IDValue idValue = (IDValue) o;
        return Objects.equals(getEntry(), idValue.getEntry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntry());
    }
}
