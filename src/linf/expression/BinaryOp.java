package linf.expression;

import linf.BaseNode;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.SemanticError;
import linf.utils.TypeError;

import java.util.ArrayList;

public abstract class BinaryOp<LeftType extends BaseNode, RightType extends BaseNode> extends Value {
    private LeftType left;
    private RightType right;
    private String op;

    public void setRight(RightType right) {
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

    public void setOp(String op) {
        this.op = op;
    }

    String getOp() {
        return op;
    }

    @Override
    public LinfType checkType() {
        LinfType type = left.checkType();
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
