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
            if (entry.getType().isReference()) {
                return "lw $t1 " + entry.getOffset() + "($fp)\n" +
                        "lw $a0 0($t1)\n";
            } else {
                return "lw $a0 " + entry.getOffset() + "($fp)\n";
            }
        } else {
            StringBuilder followChain = new StringBuilder("lw $al 2($fp)\n");
            for (int i = 0; i < nestingLevel - entry.getNestinglevel(); i++) {
                followChain.append("lw $al 0($al)\n");
            }
            return followChain + "lw $a0 " + entry.getOffset() + "($al)\n";
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
