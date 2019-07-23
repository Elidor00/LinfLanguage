package linf.expression;

public class Term extends BinaryOp<Factor, Term> {

    public void setFactor(Factor factor) {
        setLeft(factor);
    }

    Factor getFactor() {
        return getLeft();
    }
}
