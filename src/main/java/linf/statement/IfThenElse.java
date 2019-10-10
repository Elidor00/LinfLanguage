package linf.statement;

import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.error.type.UnbalancedDeletionBehaviourError;
import linf.expression.Exp;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.LinfLib;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IfThenElse extends LinfStmt {

    private final Exp exp;
    private final Block thenBranch;
    private final Block elseBranch;

    public IfThenElse(Exp exp, Block thenBranch, Block elseBranch) {
        this.exp = exp;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    Block getThenBranch() {
        return thenBranch;
    }

    Block getElseBranch() {
        return elseBranch;
    }

    @Override
    public LinfType checkType() throws TypeError {
        exp.checkType();
        thenBranch.checkType();
        elseBranch.checkType();
        HashSet<STentry> thenDel = thenBranch.getDeletedIDs();
        HashSet<STentry> elseDel = elseBranch.getDeletedIDs();
        if (!thenDel.equals(elseDel)) {
            throw new UnbalancedDeletionBehaviourError();
        }
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(exp.checkSemantics(env));
        res.addAll(thenBranch.checkSemantics(env));
        res.addAll(elseBranch.checkSemantics(env));
        return res;
    }

    @Override
    public String codeGen() {
        String end = LinfLib.freshLabel();
        String elseBranchCg = LinfLib.freshLabel();
        return exp.codeGen() +
                "li $t1 0\n" +
                "beq $a0 $t1 " + elseBranchCg.replace(":", "") + //branch if equal
                thenBranch.codeGen() +
                "b " + end.replace(":", "") +   //jump to label
                elseBranchCg +
                elseBranch.codeGen() +
                end;
    }
}
