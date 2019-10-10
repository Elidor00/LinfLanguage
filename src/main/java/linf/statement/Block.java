package linf.statement;

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
    private final HashSet<STentry> deletedIDs = new HashSet<>();
    private final HashSet<STentry> rwIDs = new HashSet<>();
    private HashMap<String, STentry> localEnv;
    private int nestingLevel;
    private boolean isAR = false;

    public Block(List<LinfStmt> list) {
        this.stmtList = List.copyOf(list);
    }

    void setLocalEnv(HashMap<String, STentry> localEnv) {
        this.localEnv = localEnv;
    }

    private HashSet<STentry> filterLocalIDs(HashSet<STentry> idSet) {
        HashSet<STentry> ids = new HashSet<>(idSet);
        for (STentry id : idSet) {
            if (nestingLevel == id.getNestinglevel()) {
                ids.remove(id);
            }
        }
        return ids;
    }

    HashSet<STentry> getDeletedIDs() {
        return filterLocalIDs(deletedIDs);
    }

    HashSet<STentry> getRwIDs() {
        return filterLocalIDs(rwIDs);
    }

    public void setAR() {
        isAR = true;
    }

    private void checkBehavior(HashSet<STentry> rwSet, HashSet<STentry> delSet) throws TypeError {
        for (STentry id : rwSet) {
            if (delSet.contains(id)) {
                throw new IncompatibleBehaviourError(id.getName());
            }
        }
    }

    private void checkDeletions(HashSet<STentry> rwSet, HashSet<STentry> delSet) throws TypeError {
        for (STentry id : delSet) {
            if (deletedIDs.contains(id)) {
                throw new DoubleDeletionError(id.getName());
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
                deletedIDs.addAll(funCall.getDeletedIDs());
                rwIDs.addAll(funCall.getRwIDs());
            } else if (stmt instanceof Block) {
                Block blk = (Block) stmt;
                checkDeletions(blk.getRwIDs(), blk.getDeletedIDs());
                deletedIDs.addAll(blk.getDeletedIDs());
                rwIDs.addAll(blk.getRwIDs());
            } else if (stmt instanceof IfThenElse) {
                IfThenElse ite = (IfThenElse) stmt;
                Block thenB = ite.getThenBranch();
                Block elseB = ite.getElseBranch();
                HashSet<STentry> thenDel = thenB.getDeletedIDs();
                HashSet<STentry> elseDel = elseB.getDeletedIDs();
                HashSet<STentry> thenRw = thenB.getRwIDs();
                HashSet<STentry> elseRw = elseB.getRwIDs();
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
            List<SemanticError> errs = stmt.checkSemantics(env);
            errors.addAll(errs);

            if (errs.size() == 0) {
                if (stmt instanceof Deletion) {
                    Deletion del = (Deletion) stmt;
                    deletedIDs.add(del.getEntry());
                }
                if (stmt instanceof VarDec) {
                    rwIDs.addAll(((VarDec) stmt).getExp().getRwIDs());
                } else if (stmt instanceof Assignment) {
                    IDValue id = ((Assignment) stmt).getId();
                    rwIDs.add(env.getLastEntry(id.toString(), env.nestingLevel));
                    rwIDs.addAll(((Assignment) stmt).getExp().getRwIDs());
                }
            }
        }


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

