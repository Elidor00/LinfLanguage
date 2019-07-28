package linf.expression;

public class Term extends BinaryOp<Factor, Term> {

    Factor getFactor() {
        return getLeft();
    }

    public void setFactor(Factor factor) {
        setLeft(factor);
    }

    @Override
    public String codeGen() {
        return null;
    }
}
