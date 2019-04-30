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

    public ComplexExtdStmtBlock getThenBranch() {
        return thenBranch;
    }

    public ComplexExtdStmtBlock getElseBranch() {
        return elseBranch;
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        exp.checkType(env);
        thenBranch.checkType(env);
        elseBranch.checkType(env);
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(exp.checkSemantics(env));
        ArrayList<SemanticError> thenErrors = thenBranch.checkSemantics(env);
        ArrayList<SemanticError> elseErrors = elseBranch.checkSemantics(env);


        if (!thenBranch.getDeletedRefs().equals(elseBranch.getDeletedRefs())) {
            res.add(new SemanticError("Deletion mismatch between \"then\" and \"else\" branches. The same identifiers must be deleted in both branches."));
        }

        res.addAll(thenErrors);
        res.addAll(elseErrors);


        return res;
    }
}
