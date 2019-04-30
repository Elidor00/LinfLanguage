package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.HashSet;


public class ComplexExtdStmtBlock extends ComplexExtdStmt {
    private ArrayList<ComplexExtdStmt> stmtList = new ArrayList<>();
    private HashSet<String> deletedRefs = new HashSet<>();

    void addStmt(ComplexExtdStmt stmt) {
        stmtList.add(stmt);
    }

    HashSet<String> getDeletedRefs() {
        return deletedRefs;
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
        ArrayList<SemanticError> errors = new ArrayList<>();

        env.openScope();

        for (ComplexExtdStmt stmt : stmtList) {
            ArrayList<SemanticError> errs = stmt.checkSemantics(env);
            errors.addAll(errs);
            if (stmt instanceof ComplexExtdStmtDeletion && ((ComplexExtdStmtDeletion) stmt).getIdType().isReference()) {
                deletedRefs.add(((ComplexExtdStmtDeletion) stmt).getId());
            }
        }

        env.closeScope();

        return errors;
    }
}
