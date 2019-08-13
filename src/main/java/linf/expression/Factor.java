package linf.expression;

import lvm.utils.Strings;

public class Factor extends BinaryOp<LinfValue, LinfValue> {


    public LinfValue getValue() {
        return getLeft();
    }

    public void setValue(LinfValue value) {
        setLeft(value);
    }

    @Override
    public String codeGen() {
        return null;
    }

    @Override
    public String toString() {
        String ret;

        if (getLeft() instanceof Exp) {
            ret = "( " + getLeft() + " )";
        } else {
            ret = getLeft().toString();
        }

        if (getRight() != null) {
            String rightString;

            if (getRight() instanceof Exp) {
                rightString = "( " + getRight() + " )";
            } else {
                rightString = getRight().toString();
            }
            ret += " " + getOp() + " " + rightString;
        }

        return ret;
    }
}