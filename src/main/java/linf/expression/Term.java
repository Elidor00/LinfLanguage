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
        String cgenTerm = this.getLeft().codeGen();
        if (this.getRight() != null) {
            cgenTerm += "push $a0 \n";
            cgenTerm += this.getRight().codeGen();
            cgenTerm += "$t1 <- top \n pop \n";
            if (this.getOp().equals("*")) {
                cgenTerm += "mult $a0 $t1 $a0 \n";
            } else {
                cgenTerm += "div $a0 $t1 $a0 \n";
            }
        }
        return cgenTerm;
    }
}
