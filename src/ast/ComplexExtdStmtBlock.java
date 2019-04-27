package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;


public class ComplexExtdStmtBlock extends ComplexExtdStmt {
    private ArrayList<ComplexExtdStmt> stmtList = new ArrayList<>();

    public ComplexExtdStmtBlock() {
    }

    public void addStmt(ComplexExtdStmt stmt) {
        stmtList.add(stmt);
    }

    @Override
    public ComplexExtdType checkType(Environment env) {
        for (ComplexExtdStmt stmt : stmtList) {
            stmt.checkType(env);
        }
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //declare resulting list
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        env.openScope();

        for (ComplexExtdStmt stmt : stmtList) {
            errors.addAll(stmt.checkSemantics(env));
        }

        env.closeScope();

        return errors;
    }
}
