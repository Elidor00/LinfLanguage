package linf.expression;

import linf.Node;
import linf.error.behaviour.BehaviourError;
import linf.error.semantic.SemanticError;
import linf.error.type.IncompatibleTypesError;
import linf.error.type.TypeError;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;
import java.util.List;

public abstract class BinaryOp<LeftType extends Node, RightType extends Node> extends LinfValue {
    private LeftType left;
    private RightType right;
    private String op;

    RightType getRight() {
        return right;
    }

    public void setRight(RightType right) {
        this.right = right;
    }

    LeftType getLeft() {
        return left;
    }

    void setLeft(LeftType left) {
        this.left = left;
    }

    String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    @Override
    public LinfType checkType() throws TypeError {
        LinfType type = left.checkType();
        if (right != null) {
            LinfType rhSideType = right.checkType();
            if (!type.getClass().equals(rhSideType.getClass())) {
                throw new IncompatibleTypesError(type, rhSideType);
            }
        }
        return type;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
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
