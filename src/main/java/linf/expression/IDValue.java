package linf.expression;

import linf.error.semantic.SemanticError;
import linf.error.semantic.UnboundSymbolError;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.LinfLib;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.List;
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
        this.nestingLevel = entry.getNestinglevel();
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
    public List<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();
        entry = env.getStEntry(value);
        if (entry == null) {
            res.add(new UnboundSymbolError(value));
        } else {
            setEntry(entry);
        }
        return res;
    }

    private String codeGen(String op) {
        int distance = nestingLevel - entry.getNestinglevel();
        if (entry.getType().isReference()) {
            STentry referred = entry.getType().getRefTo();
            if (distance > 0) {
                return LinfLib.followChain(distance) +
                        "lw $al " + entry.getOffset() + "($al)\n" +
                        String.format("%s $a0 " + referred.getOffset() + "($al)\n", op);
            } else {
                return "lw $al " + entry.getOffset() + "($fp)\n" +
                        String.format("%s $a0 " + referred.getOffset() + "($al)\n", op);
            }
        } else if (distance > 0) {
            // free variable
            return LinfLib.followChain(distance) +
                    String.format("%s $a0 " + entry.getOffset() + "($al)\n", op);
        } else {
            return String.format("%s $a0 " + entry.getOffset() + "($fp)\n", op);
        }
    }

    public String LSideCodeGen() {
        return this.codeGen("sw");
    }

    @Override
    public String codeGen() {
        return this.codeGen("lw");
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
