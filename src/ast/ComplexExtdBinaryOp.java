package ast;

import utils.Environment;
import utils.SemanticError;
import utils.TypeError;

import java.util.ArrayList;

public abstract class ComplexExtdBinaryOp<LeftType extends Node, RightType extends Node> extends ComplexExtdValue {
    private LeftType left;
    private RightType right;
    private String op;

    void setRight(RightType right) {
        this.right = right;
    }

    RightType getRight() {
        return right;
    }

    void setLeft(LeftType left) {
        this.left = left;
    }

    LeftType getLeft() {
        return left;
    }

    void setOp(String op) {
        this.op = op;
    }

    String getOp() {
        return op;
    }

    @Override
    public ComplexExtdType checkType() {
        ComplexExtdType type = left.checkType();
        if (right != null) {
            if (!type.getClass().equals(right.checkType().getClass())) {
                System.out.println(new TypeError("Cannot type " + op + " in \"" + toString() + "\" with this parameters: " + type + " and " + right.checkType()));
                System.exit(-1);
            }
        }
        return type;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(left.checkSemantics(env));

        if (right != null) {
            res.addAll(right.checkSemantics(env));
        }

        return res;
    }

    @Override
    public String toString() {
        String ret = left.toString();

        if (getRight() != null) {
            ret += " " + op + " " + right.toString();
        }

        return ret;
    }
}
