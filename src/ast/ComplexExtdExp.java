package ast;

import java.util.ArrayList;

public class ComplexExtdExp implements Node {
    private ComplexExtdType type;
    private boolean isNegative;
    private ComplexExtdTerm term;
    private String op;
    private ComplexExtdExp right;

    public ComplexExtdTerm getTerm() {
        return term;
    }

    public void setTerm(ComplexExtdTerm term) {
        this.term = term;
    }

    public void setRight(ComplexExtdExp right) {
        this.right = right;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public ComplexExtdType getType() {
        return type;
    }

    public boolean isNegative() {
        return isNegative;
    }

    public void setNegative(boolean negative) {
        isNegative = negative;
    }

    public ComplexExtdType setValueType() {
        type = term.getFactor().getValue().computeType();
        return type;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        if (right != null) {
            if (type.getClass().equals(right.checkType(env).getClass())) {
                return type;
            } else {
                System.out.println(new TypeError("Wrong type in exp" + this.toString()));
                System.exit(-1);
            }
        }
        return null; // this line will never execute
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(term.checkSemantics(env));

        if (right != null) {
            res.addAll(right.checkSemantics(env));
        }

        return res;
    }


}
