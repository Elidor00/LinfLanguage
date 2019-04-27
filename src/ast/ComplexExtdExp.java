package ast;

public class ComplexExtdExp extends ComplexExtdBinaryOp<ComplexExtdTerm, ComplexExtdExp> {
    private boolean isNegative;

    void setTerm(ComplexExtdTerm term) {
        setLeft(term);
    }

    public boolean isNegative() {
        return isNegative;
    }

    void setNegative(boolean negative) {
        isNegative = negative;
    }

    public boolean isID() {
        ComplexExtdTerm term = getLeft();
        ComplexExtdFactor factor = term.getFactor();
        ComplexExtdValue value = factor.getValue();

        return term.getRight() == null &&
                factor.getRight() == null &&
                value instanceof ComplexExtdIDValue;
    }
}
