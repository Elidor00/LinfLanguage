package linf.statement;

import linf.expression.Exp;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.SemanticError;

import java.util.ArrayList;

public class StmtIfThenElse extends Stmt {

    private Exp exp;
    private StmtBlock thenBranch;
    private StmtBlock elseBranch;

    public StmtIfThenElse(Exp exp, StmtBlock thenBranch, StmtBlock elseBranch) {
        this.exp = exp;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    StmtBlock getThenBranch() {
        return thenBranch;
    }

    StmtBlock getElseBranch() {
        return elseBranch;
    }

    @Override
    public LinfType checkType() {
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
}
