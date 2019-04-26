package ast;

import java.util.ArrayList;

public class ComplexExtdFactor extends ComplexExtdTerm {

    private ComplexExtdValue value;
    private String op;
    private ComplexExtdValue right;

    public void setValue(ComplexExtdValue value) {
        this.value = value;
    }

    public void setRight(ComplexExtdValue right) {
        this.right = right;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public ComplexExtdValue getValue() {
        return value;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        ComplexExtdType type = value.checkType(env);
        if (right != null) if (type.getClass().equals(right.checkType(env).getClass())) {
            return type;
        } else {
            System.out.println(new TypeError("Cannot type " + op + " with this parameters: " + type.getClass() + " and " + right.checkType(env).getClass()));
            System.exit(-1);
        }
        return null; // this line will never execute
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(value.checkSemantics(env));

        if (right != null) {
            res.addAll(right.checkSemantics(env));
        }

        return res;

    }
}