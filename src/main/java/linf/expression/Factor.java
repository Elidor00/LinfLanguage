package linf.expression;

import linf.utils.LinfLib;

import java.util.List;

public class Factor extends BinaryOp<LinfValue, LinfValue> {

    public LinfValue getValue() {
        return getLeft();
    }

    public void setValue(LinfValue value) {
        setLeft(value);
    }

    @Override
    public String codeGen() {
        String cgenFactor = this.getLeft().codeGen();
        if (this.getRight() != null) {
            String endBranchLabel = LinfLib.freshLabel().replace(":", "");
            if (!List.of("&&", "||").contains(getOp())) {
                String trueBranchLabel = LinfLib.freshLabel().replace(":", "");
                cgenFactor += "push $a0\n";
                cgenFactor += getRight().codeGen();
                cgenFactor += "top $t1\npop\n";

                switch (getOp()) { //ROP: '==' | '>' | '<' | '<=' | '>=' | '!='
                    case "==":
                        cgenFactor += "beq $t1 $a0 " + trueBranchLabel;
                        break;
                    case ">":
                        cgenFactor += "bgr $t1 $a0 " + trueBranchLabel;
                        break;
                    case "<":
                        cgenFactor += "blr $t1 $a0 " + trueBranchLabel;
                        break;
                    case "<=":
                        cgenFactor += "blre $t1 $a0 " + trueBranchLabel;
                        break;
                    case ">=":
                        cgenFactor += "bgre $t1 $a0 " + trueBranchLabel;
                        break;
                    case "!=":
                        cgenFactor += "bne $t1 $a0 " + trueBranchLabel;
                        break;
                }

                cgenFactor += "li $a0 0\nb " + endBranchLabel;
                cgenFactor += trueBranchLabel.replace("\n", "") + ":\nli $a0 1\n";
                cgenFactor += endBranchLabel.replace("\n", "") + ":\n";

            } else {
                switch (this.getOp()) { //op: '&&' | '||'
                    case "&&":
                        cgenFactor += "li $t1 0\n";
                        cgenFactor += "beq $a0 $t1 " + endBranchLabel;
                        cgenFactor += this.getRight().codeGen();
                        cgenFactor += endBranchLabel.replace("\n", "") + ":\n";
                        break;
                    case "||":
                        cgenFactor += "li $t1 1\n";
                        cgenFactor += "beq $a0 $t1 " + endBranchLabel;
                        cgenFactor += this.getRight().codeGen();
                        cgenFactor += endBranchLabel.replace("\n", "") + ":\n";
                        break;
                }
            }
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