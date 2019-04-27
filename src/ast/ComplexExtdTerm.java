package ast;

public class ComplexExtdTerm extends ComplexExtdBinaryOp<ComplexExtdFactor, ComplexExtdTerm> {

    void setFactor(ComplexExtdFactor factor) {
        setLeft(factor);
    }

    ComplexExtdFactor getFactor() {
        return getLeft();
    }
}
