package linf.statement;

import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;
import linf.utils.SemanticError;
import linf.utils.TypeError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class StmtBlock extends Stmt {
    private ArrayList<Stmt> stmtList = new ArrayList<>();
    private HashSet<IDValue> deletedIDs = new HashSet<>();
    private HashSet<IDValue> rwIDs = new HashSet<>();
    private HashMap<String, STentry> localEnv;


    void setLocalEnv(HashMap<String, STentry> localEnv) {
        this.localEnv = localEnv;
    }

    public void addStmt(Stmt stmt) {
        stmtList.add(stmt);
    }

    private HashSet<IDValue> filterLocalIDs(HashSet<IDValue> idSet) {
        HashSet<IDValue> ids = new HashSet<>(idSet);
        for (IDValue id : idSet) {
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

    HashSet<IDValue> getDeletedIDs() {
        return filterLocalIDs(deletedIDs);
    }

    HashSet<IDValue> getRwIDs() {
        return filterLocalIDs(rwIDs);
    }

    private void checkDeletions(HashSet<IDValue> rwSet, HashSet<IDValue> delSet) {
        for (IDValue id : rwSet) {
            if (delSet.contains(id)) {
                System.out.println(new TypeError("Deletion Error: Both RW and DEL access on identifier \"" + id + "\"."));
                System.exit(-1);
            }
        }
        for (IDValue id : delSet) {
            if (deletedIDs.contains(id)) {
                System.out.println(new TypeError("Double Deletion. Identifier \"" + id + "\" will be deleted two or more times."));
                System.exit(-1);
            }
        }
        rwIDs.addAll(rwSet);
        deletedIDs.addAll(delSet);
    }

    @Override
    public LinfType checkType() {
        for (Stmt stmt : stmtList) {
            stmt.checkType();
            // check deletions
            if (stmt instanceof StmtFunCall) {
                StmtFunCall funCall = (StmtFunCall) stmt;
                checkDeletions(funCall.getRwIDs(), funCall.getDeletedIDs());
            } else if (stmt instanceof StmtBlock) {
                StmtBlock blk = (StmtBlock) stmt;
                checkDeletions(blk.getRwIDs(), blk.getDeletedIDs());
            } else if (stmt instanceof StmtIfThenElse) {
                StmtIfThenElse ite = (StmtIfThenElse) stmt;
                StmtBlock then = ite.getThenBranch();
                StmtBlock elseB = ite.getElseBranch();
                HashSet<IDValue> thenDel = then.getDeletedIDs();
                HashSet<IDValue> elseDel = elseB.getDeletedIDs();
                HashSet<IDValue> thenRw = then.getRwIDs();
                HashSet<IDValue> elseRw = elseB.getRwIDs();
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

        for (Stmt stmt : stmtList) {
            ArrayList<SemanticError> errs = stmt.checkSemantics(env);
            errors.addAll(errs);

            if (stmt instanceof StmtDeletion) {
                IDValue id = ((StmtDeletion) stmt).getId();
                if (deletedIDs.contains(id)) {
                    System.out.println(new TypeError("Double Deletion. Identifier \"" + id + "\" will be deleted two or more times."));
                    System.exit(-1);
                }
                if (!env.isLocalName(id)) {
                    deletedIDs.add(((StmtDeletion) stmt).getId());
                }
            } else if (stmt instanceof StmtVarDec) {
                rwIDs.addAll(((StmtVarDec) stmt).getExp().getRwIDs());
            } else if (stmt instanceof StmtAssignment) {
                rwIDs.add(((StmtAssignment) stmt).getId());
                rwIDs.addAll(((StmtAssignment) stmt).getExp().getRwIDs());
            }
        }

        localEnv = env.local();

        env.closeScope();

        return errors;
    }

}
