package ast;

public class ComplexExtdFactor extends ComplexExtdBinaryOp<ComplexExtdValue, ComplexExtdValue> {


    public void setValue(ComplexExtdValue value) {
        setLeft(value);
    }

    public ComplexExtdValue getValue() {
        return getLeft();
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