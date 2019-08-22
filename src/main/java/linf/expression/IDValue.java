package linf.expression;

import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundSymbolError;
import linf.type.FunType;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.Objects;

public class IDValue extends LinfValue {
    private STentry entry;
    private int nestingLevel;

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

    public boolean isParameter() {
        return entry.getType().isParameter();
    }

    public void setParameter(boolean parameter) {
        entry.getType().setParameter(parameter);
    }

    @Override
    public LinfType checkType() {
        return getType();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();
        entry = env.getStEntry(value);
        nestingLevel = env.nestingLevel;
        if (entry == null) {
            res.add(new UnboundSymbolError(value));
        } else {
            setType(entry.getType());
        }
        return res;
    }

    @Override
    public String codeGen() {
        if (entry.getType() instanceof FunType) {
            return ((FunType) entry.getType()).getFunLabel();
        } else if (isParameter()) {
            // Local to AR
            return "lw $a0 " + entry.getOffset() + "($fp)\n";
        } else {
            int distance = nestingLevel - entry.getNestinglevel();
            if (distance > 0) {
                return  "lw $al 2($fp)\n" +
                        "lw $al 2($al)\n".repeat(distance - 1)+
                        "lw $a0 " + entry.getOffset() + "($al)\n";
            } else {
                return "lw $a0 " + entry.getOffset() + "($fp)\n";
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IDValue idValue = (IDValue) o;
        return Objects.equals(getEntry(), idValue.getEntry()) && nestingLevel == idValue.nestingLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntry());
    }
}
