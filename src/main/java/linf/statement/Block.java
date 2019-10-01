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
import java.util.List;


public class Block extends LinfStmt {
    private final List<LinfStmt> stmtList;
    private final HashSet<IDValue> deletedIDs = new HashSet<>();
    private final HashSet<IDValue> rwIDs = new HashSet<>();
    private HashMap<String, STentry> localEnv;
    private int nestingLevel;
    private boolean isAR = false;

    public Block(List<LinfStmt> list) {
        this.stmtList = List.copyOf(list);
    }

    void setLocalEnv(HashMap<String, STentry> localEnv) {
        this.localEnv = localEnv;
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

    public void setAR() {
        isAR = true;
    }

    private void checkBehavior(HashSet<IDValue> rwSet, HashSet<IDValue> delSet) throws TypeError {
        for (IDValue id : rwSet) {
            if (delSet.contains(id)) {
                throw new IncompatibleBehaviourError(id);
            }
        }
    }

    private void checkDeletions(HashSet<IDValue> rwSet, HashSet<IDValue> delSet) throws TypeError {
        for (IDValue id : delSet) {
            if (getDeletedIDs().contains(id)) {
                throw new DoubleDeletionError(id);
            }
        }
        checkBehavior(rwSet, delSet);
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
                Block thenB = ite.getThenBranch();
                Block elseB = ite.getElseBranch();
                HashSet<IDValue> thenDel = thenB.getDeletedIDs();
                HashSet<IDValue> elseDel = elseB.getDeletedIDs();
                HashSet<IDValue> thenRw = thenB.getRwIDs();
                HashSet<IDValue> elseRw = elseB.getRwIDs();
                if (!thenDel.equals(elseDel)) {
                    throw new UnbalancedDeletionBehaviourError();
                }
                checkDeletions(thenRw, thenDel);
                checkDeletions(elseRw, elseDel);
                deletedIDs.addAll(thenDel);
                rwIDs.addAll(thenRw);
                rwIDs.addAll(elseRw);
            }
            checkBehavior(rwIDs, deletedIDs);
        }
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        env.openScope(localEnv);
        nestingLevel = env.nestingLevel;

        for (LinfStmt stmt : stmtList) {
            errors.addAll(stmt.checkSemantics(env));

            if (stmt instanceof Deletion) {
                IDValue id = ((Deletion) stmt).getId();
                if (deletedIDs.contains(id)) {
                    errors.add(new IllegalDeletionError(id));
                } else {
                    deletedIDs.add(id);
                }
            } else if (stmt instanceof VarDec) {
                rwIDs.addAll(((VarDec) stmt).getExp().getRwIDs());
            } else if (stmt instanceof Assignment) {
                rwIDs.add(((Assignment) stmt).getId());
                rwIDs.addAll(((Assignment) stmt).getExp().getRwIDs());
            } else if (stmt instanceof FunCall) {
                deletedIDs.addAll(((FunCall) stmt).getDeletedIDs());
                rwIDs.addAll(((FunCall) stmt).getRwIDs());
            }
        }

        localEnv = env.local();
        env.closeScope();
        return errors;
    }

    @Override
    public String codeGen() {
        StringBuilder code = new StringBuilder();
        // check varDec inside block
        long numVarDec = stmtList.stream()
                .filter((stmt) -> stmt instanceof VarDec)
                .count();

        code.append("move $fp $sp\n");
        // codegen statement inside block
        for (LinfStmt statement : stmtList) {
            code.append(statement.codeGen());
        }

        if (numVarDec > 0)
            code.append("addi $sp $sp ").append(numVarDec).append("\n");
        if (!isAR) {
            if (nestingLevel == 0) {
                code.insert(0, "push $t1\n".repeat(3))
                        .insert(0, "subi $t1 $sp 3\n");
            } else {
                code.insert(0, "push $fp\n".repeat(3));
            }
            // restore $fp to point to the AR of parent
            code.append("lw $fp 2($sp)\n");
            // pop return address, access link and control link
            code.append("addi $sp $sp 3\n");
        }
        return code.toString();
    }
}

