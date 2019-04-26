package ast;

import java.util.ArrayList;

public class ComplexExtdTerm extends ComplexExtdExp {
    private ComplexExtdFactor factor;
    private String op;
    private ComplexExtdTerm right;

    public void setFactor(ComplexExtdFactor factor) {
        this.factor = factor;
    }

    public void setRight(ComplexExtdTerm right) {
        this.right = right;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public ComplexExtdFactor getFactor() {
        return factor;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        ComplexExtdType type = factor.checkType(env);
        if (right != null) {
            if (type.getClass().equals(right.checkType(env).getClass())) {
                return type;
            } else {
                System.out.println(new TypeError("Cannot type " + op + " with this parameters: " + type.getClass() + " and " + right.checkType(env).getClass()));
                System.exit(-1);

            }
        }
        return null; // this line will never execute
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(factor.checkSemantics(env));

        if (right != null) {
            res.addAll(right.checkSemantics(env));
        }

        return res;
    }
}
