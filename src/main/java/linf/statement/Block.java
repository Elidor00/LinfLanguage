package linf.statement;

import linf.error.semantic.IllegalDeletionError;
import linf.error.semantic.SemanticError;
import linf.error.type.DoubleDeletionError;
import linf.error.type.IncompatibleBehaviourError;
import linf.error.type.TypeError;
import linf.error.type.UnbalancedDeletionBehaviourError;
import linf.expression.IDValue;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Block extends LinfStmt {
    private final ArrayList<LinfStmt> stmtList = new ArrayList<>();
    private final HashSet<IDValue> deletedIDs = new HashSet<>();
    private final HashSet<IDValue> rwIDs = new HashSet<>();
    private HashMap<String, STentry> localEnv;


    void setLocalEnv(HashMap<String, STentry> localEnv) {
        this.localEnv = localEnv;
    }

    public void addStmt(LinfStmt stmt) {
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

    private void checkDeletions(HashSet<IDValue> rwSet, HashSet<IDValue> delSet) throws TypeError {
        for (IDValue id : rwSet) {
            if (delSet.contains(id)) {
                throw new IncompatibleBehaviourError(id);
            }
        }
        for (IDValue id : delSet) {
            if (deletedIDs.contains(id)) {
                throw new DoubleDeletionError(id);
            }
        }
        rwIDs.addAll(rwSet);
        deletedIDs.addAll(delSet);
    }

    @Override
    public LinfType checkType() throws TypeError {
        for (LinfStmt stmt : stmtList) {
            stmt.checkType();
            // check deletions
            if (stmt instanceof FunCall) {
                FunCall funCall = (FunCall) stmt;
                checkDeletions(funCall.getRwIDs(), funCall.getDeletedIDs());
            } else if (stmt instanceof Block) {
                Block blk = (Block) stmt;
                checkDeletions(blk.getRwIDs(), blk.getDeletedIDs());
            } else if (stmt instanceof IfThenElse) {
                IfThenElse ite = (IfThenElse) stmt;
                Block then = ite.getThenBranch();
                Block elseB = ite.getElseBranch();
                HashSet<IDValue> thenDel = then.getDeletedIDs();
                HashSet<IDValue> elseDel = elseB.getDeletedIDs();
                HashSet<IDValue> thenRw = then.getRwIDs();
                HashSet<IDValue> elseRw = elseB.getRwIDs();
                if (!thenDel.equals(elseDel)) {
                    throw new UnbalancedDeletionBehaviourError();
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

        for (LinfStmt stmt : stmtList) {
            ArrayList<SemanticError> errs = stmt.checkSemantics(env);
            errors.addAll(errs);

            if (stmt instanceof Deletion) {
                IDValue id = ((Deletion) stmt).getId();
                if (deletedIDs.contains(id)) {
                    errors.add(new IllegalDeletionError(id));
                }
                if (!env.isLocalName(id)) {
                    deletedIDs.add(((Deletion) stmt).getId());
                }
            } else if (stmt instanceof VarDec) {
                rwIDs.addAll(((VarDec) stmt).getExp().getRwIDs());
            } else if (stmt instanceof Assignment) {
                rwIDs.add(((Assignment) stmt).getId());
                rwIDs.addAll(((Assignment) stmt).getExp().getRwIDs());
            }
        }

        localEnv = env.local();

        env.closeScope();

        return errors;
    }

    @Override
    public String codeGen() {
        StringBuilder code = new StringBuilder(new String(""));
        int counterVarDec = 0;
        code.append("push $fp\n");
        //check varDec inside block
        for (LinfStmt statement: stmtList){
            if (statement instanceof VarDec) {
                counterVarDec++;
            }
        }
        code.append("addi $sp $sp ").append(counterVarDec * 4).append("\n");
        code.append("push $fp\n");
        code.append("move $fp $sp\n");
        //check FunDec inside block
        for (LinfStmt statement: stmtList){
            if (statement instanceof FunDec) {
                code.append(statement.codeGen());
            }
        }
        code.append("pop\n");
        code.append("$fp <- top\n");
        code.append("pop\n");
        return code.toString();
    }
}

