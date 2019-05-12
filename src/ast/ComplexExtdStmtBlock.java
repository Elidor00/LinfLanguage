package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;
import utils.TypeError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class ComplexExtdStmtBlock extends ComplexExtdStmt {
    private ArrayList<ComplexExtdStmt> stmtList = new ArrayList<>();
    private HashSet<ComplexExtdIDValue> deletedIDs = new HashSet<>();
    private HashSet<ComplexExtdIDValue> rwIDs = new HashSet<>();
    private HashMap<String, STentry> localEnv;


    void setLocalEnv(HashMap<String, STentry> localEnv) {
        this.localEnv = localEnv;
    }

    void addStmt(ComplexExtdStmt stmt) {
        stmtList.add(stmt);
    }

    private HashSet<ComplexExtdIDValue> filterLocalIDs(HashSet<ComplexExtdIDValue> idSet) {
        HashSet<ComplexExtdIDValue> ids = new HashSet<>(idSet);
        for (ComplexExtdIDValue id : idSet) {
            String idName = id.toString();
            if (localEnv.containsKey(idName)) {
                STentry localEntry = localEnv.get(idName);
                STentry idEntry = id.getEntry();
                if (localEntry.getNestinglevel() == idEntry.getNestinglevel()) {
                    ids.remove(id);
                }
            }
        }
        return ids;
    }

    HashSet<ComplexExtdIDValue> getDeletedIDs() {
        return filterLocalIDs(deletedIDs);
    }

    HashSet<ComplexExtdIDValue> getRwIDs() {
        return filterLocalIDs(rwIDs);
    }

    private void checkDeletions(HashSet<ComplexExtdIDValue> rwSet, HashSet<ComplexExtdIDValue> delSet) {
        for (ComplexExtdIDValue id : rwSet) {
            if (delSet.contains(id)) {
                System.out.println(new TypeError("Deletion Error: Both RW and DEL access on identifier \"" + id + "\"."));
                System.exit(-1);
            }
        }
        for (ComplexExtdIDValue id : delSet) {
            if (deletedIDs.contains(id)) {
                System.out.println(new TypeError("Double Deletion. Identifier \"" + id + "\" will be deleted two or more times."));
                System.exit(-1);
            }
        }
        rwIDs.addAll(rwSet);
        deletedIDs.addAll(delSet);
    }

    @Override
    public ComplexExtdType checkType() {
        for (ComplexExtdStmt stmt : stmtList) {
            stmt.checkType();
            // check deletions
            if (stmt instanceof ComplexExtdStmtFunCall) {
                ComplexExtdStmtFunCall funCall = (ComplexExtdStmtFunCall) stmt;
                checkDeletions(funCall.getRwIDs(), funCall.getDeletedIDs());
            } else if (stmt instanceof ComplexExtdStmtBlock) {
                ComplexExtdStmtBlock blk = (ComplexExtdStmtBlock) stmt;
                checkDeletions(blk.getRwIDs(), blk.getDeletedIDs());
            } else if (stmt instanceof ComplexExtdStmtIfThenElse) {
                ComplexExtdStmtIfThenElse ite = (ComplexExtdStmtIfThenElse) stmt;
                ComplexExtdStmtBlock then = ite.getThenBranch();
                ComplexExtdStmtBlock elseB = ite.getElseBranch();
                HashSet<ComplexExtdIDValue> thenDel = then.getDeletedIDs();
                HashSet<ComplexExtdIDValue> elseDel = elseB.getDeletedIDs();
                HashSet<ComplexExtdIDValue> thenRw = then.getRwIDs();
                HashSet<ComplexExtdIDValue> elseRw = elseB.getRwIDs();
                if (!thenDel.equals(elseDel)) {
                    System.out.println(new TypeError("Deletion mismatch between \"then\" and \"else\" branches. The same identifiers must be deleted in both branches."));
                    System.exit(-1);
                }
                checkDeletions(thenRw, thenDel);
                checkDeletions(elseRw, elseDel);
            }
        }
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        env.openScope(localEnv);

        for (ComplexExtdStmt stmt : stmtList) {
            ArrayList<SemanticError> errs = stmt.checkSemantics(env);
            errors.addAll(errs);

            if (stmt instanceof ComplexExtdStmtDeletion) {
                ComplexExtdIDValue id = ((ComplexExtdStmtDeletion) stmt).getId();
                if (deletedIDs.contains(id)) {
                    System.out.println(new TypeError("Double Deletion. Identifier \"" + id + "\" will be deleted two or more times."));
                    System.exit(-1);
                }
                if (!env.isLocalName(id)) {
                    deletedIDs.add(((ComplexExtdStmtDeletion) stmt).getId());
                }
            } else if (stmt instanceof ComplexExtdStmtVarDec) {
                rwIDs.addAll(((ComplexExtdStmtVarDec) stmt).getExp().getRwIDs());
            } else if (stmt instanceof ComplexExtdStmtAssignment) {
                rwIDs.add(((ComplexExtdStmtAssignment) stmt).getId());
                rwIDs.addAll(((ComplexExtdStmtAssignment) stmt).getExp().getRwIDs());
            }
        }

        localEnv = env.local();

        env.closeScope();

        return errors;
    }

}
