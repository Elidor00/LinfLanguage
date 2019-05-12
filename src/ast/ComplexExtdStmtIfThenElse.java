package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ComplexExtdStmtIfThenElse extends ComplexExtdStmt {

    private ComplexExtdExp exp;
    private ComplexExtdStmtBlock thenBranch;
    private ComplexExtdStmtBlock elseBranch;

    ComplexExtdStmtIfThenElse(ComplexExtdExp exp, ComplexExtdStmtBlock thenBranch, ComplexExtdStmtBlock elseBranch) {
        this.exp = exp;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    ComplexExtdStmtBlock getThenBranch() {
        return thenBranch;
    }

    ComplexExtdStmtBlock getElseBranch() {
        return elseBranch;
    }

    @Override
    public ComplexExtdType checkType() {
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
