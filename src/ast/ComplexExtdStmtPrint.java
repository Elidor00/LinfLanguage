package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ComplexExtdStmtPrint extends ComplexExtdStmt {
    @Override
    public ComplexExtdType checkType(Environment env) {
        return super.checkType(env);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }
}
