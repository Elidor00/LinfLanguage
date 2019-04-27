package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ComplexExtdFactor extends ComplexExtdBinaryOp<ComplexExtdValue, ComplexExtdValue> {


    public void setValue(ComplexExtdValue value) {
        setLeft(value);
    }

    public ComplexExtdValue getValue() {
        return getLeft();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ComplexExtdValue value = getLeft();
        ComplexExtdValue right = getRight();
        ArrayList<SemanticError> res = new ArrayList<>(value.checkSemantics(env));

        if (value instanceof ComplexExtdIDValue) {
            value.setType(env.getStEntry(value.toString()).getType());
        }

        if (right != null) {
            res.addAll(right.checkSemantics(env));

            if (right instanceof ComplexExtdIDValue) {
                right.setType(env.getStEntry(right.toString()).getType());
            }
        }

        return res;

    }

    @Override
    public String toString() {
        String ret;

        if (getLeft() instanceof ComplexExtdExp) {
            ret = "( " + getLeft() + " )";
        } else {
            ret = getLeft().toString();
        }

        if (getRight() != null) {
            String rightString;

            if (getRight() instanceof ComplexExtdExp) {
                rightString = "( " + getRight() + " )";
            } else {
                rightString = getRight().toString();
            }
            ret += " " + getOp() + " " + rightString;
        }

        return ret;
    }
}