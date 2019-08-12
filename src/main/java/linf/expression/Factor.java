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
        String trueBranchLabel = Strings.freshLabel();
        String endBranchLabel = Strings.freshLabel();
        String cgenFactor = this.getLeft().codeGen();
        if (this.getRight() != null) {
            cgenFactor += "push $a0 \n";
            cgenFactor += this.getRight().codeGen();
            cgenFactor += "$t1 <- top \n pop \n";

            switch (this.getOp()){ //ROP: '==' | '>' | '<' | '<=' | '>=' | '!=' ;
                case "==":
                    cgenFactor += "beq $t1 $a0 " + trueBranchLabel + "\n";
                    break;
                case ">":
                    cgenFactor += "bg $t1 $a0 " + trueBranchLabel + "\n";
                    break;
                case "<":
                    cgenFactor += "bl $t1 $a0 " + trueBranchLabel + "\n";
                    break;
                case "<=":
                    cgenFactor += "ble $t1 $a0 " + trueBranchLabel + "\n";
                    break;
                case ">=":
                    cgenFactor += "bge $t1 $a0 " + trueBranchLabel + "\n";
                    break;
                case "!=":
                    cgenFactor += "bne $t1 $a0 " + trueBranchLabel + "\n";
                    break;
                    //TODO: op=('&&' | '||') factor operatori booleani
            }

            cgenFactor += "li $a0 0 \n b " + endBranchLabel + "\n";
            cgenFactor += trueBranchLabel + ":\n li $a0 1 \n";
            cgenFactor += endBranchLabel + ":\n";
        }
        return cgenFactor;
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