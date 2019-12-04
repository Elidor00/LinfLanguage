package linf.statement;

import linf.error.behaviour.BehaviourError;
import linf.error.behaviour.UnbalancedDeletionBehaviourError;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.expression.Exp;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.LinfLib;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IfThenElse implements DeletingStatement, RWStatement {

    private final Exp exp;
    private final Block thenBranch;
    private final Block elseBranch;

    public IfThenElse(Exp exp, Block thenBranch, Block elseBranch) {
        this.exp = exp;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public HashSet<STentry> getRWSet() {
        HashSet<STentry> set = thenBranch.getRWSet();
        set.addAll(elseBranch.getRWSet());
        return set;
    }

    @Override
    public HashSet<STentry> getDelSet() {
        return thenBranch.getDelSet();
    }

    @Override
    public LinfType checkType() throws TypeError {
        exp.checkType();
        thenBranch.checkType();
        elseBranch.checkType();
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
        ArrayList<SemanticError> res = new ArrayList<>(exp.checkSemantics(env));
        res.addAll(thenBranch.checkSemantics(env));
        res.addAll(elseBranch.checkSemantics(env));

        if (res.size() == 0) {
            HashSet<STentry> thenDel = thenBranch.getDelSet();
            HashSet<STentry> elseDel = elseBranch.getDelSet();

            if (!thenDel.equals(elseDel)) {
                throw new UnbalancedDeletionBehaviourError();
            }
        }

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
