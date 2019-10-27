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

    private void setEntry(STentry entry) {
        this.entry = entry;
        this.setType(entry.getType());
    }

    @Override
    public LinfType checkType() {
        return getType();
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();
        STentry envEntry = env.getStEntry(value);
        nestingLevel = env.getNestingLevel();

        if (envEntry == null) {
            res.add(new UnboundSymbolError(this));
        } else {
            setEntry(envEntry);
            setType(envEntry.getType());
        }
        return res;
    }

    private String codeGen(String op) {
        int distance = nestingLevel - entry.getNestinglevel();
        if (entry.getType().isReference()) {
            String code;
            if (distance > 0) {
                code = LinfLib.followCl(distance) +
                        "lw $al " + entry.getOffset() + "($al)\n";
            } else {
                code = "lw $al " + entry.getOffset() + "($fp)\n";
            }
            return code + String.format("%s $a0 0($al)\n", op);
        } else if (distance > 0) {
            // free variable
            return LinfLib.followAl(distance) +
                    String.format("%s $a0 " + entry.getOffset() + "($al)\n", op);
        } else {
            return String.format("%s $a0 " + entry.getOffset() + "($fp)\n", op);
        }
    }

    public String loadAddress() {
        String code = "";
        int distance = nestingLevel - entry.getNestinglevel();
        if (entry.getType().isReference()) {
            if (distance > 0) {
                code += LinfLib.followCl(distance) +
                        "lw $al " + entry.getOffset() + "($al)\n";
            } else {
                code += "lw $al " + entry.getOffset() + "($fp)\n";
            }
            code += "move $a0 $al\n";
        } else {
            if (distance > 0) {
                // free variable
                return LinfLib.followAl(distance) +
                        "move $a0 $al\n";
            } else {
                code += "move $a0 $fp\n";
            }
            if (entry.getOffset() != 0) {
                code += "addi $a0 $a0 " + entry.getOffset() + "\n";
            }
        }
        return code;
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
        return nestingLevel == idValue.nestingLevel && value.equals(idValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
