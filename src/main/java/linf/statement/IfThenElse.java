package linf.statement;

import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.expression.Exp;
import linf.type.LinfType;
import linf.utils.Environment;

import java.util.ArrayList;

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
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(exp.checkSemantics(env));
        res.addAll(thenBranch.checkSemantics(env));
        res.addAll(elseBranch.checkSemantics(env));
        return res;
    }

    @Override
    public String codeGen() {
        return null;
    }
}
