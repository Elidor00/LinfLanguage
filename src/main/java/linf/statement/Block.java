package linf.statement;

import linf.error.behaviour.BehaviourError;
import linf.error.semantic.DoubleDeletionError;
import linf.error.semantic.SemanticError;
import linf.error.type.TypeError;
import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.STentry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class Block implements DeletingStatement, RWStatement {
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

    public HashSet<STentry> getDelSet() {
        return filterLocalIDs(deletedIDs);
    }

    public HashSet<STentry> getRWSet() {
        return filterLocalIDs(rwIDs);
    }

    public void setAR() {
        isAR = true;
    }

    @Override
    public LinfType checkType() throws TypeError {
        for (LinfStmt stmt : stmtList) {
            stmt.checkType();
        }
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) throws BehaviourError {
        ArrayList<SemanticError> errors = new ArrayList<>();

        env.openScope(localEnv);
        nestingLevel = env.getNestingLevel();

        outer:
        for (LinfStmt stmt : stmtList) {
            List<SemanticError> errs = stmt.checkSemantics(env);
            errors.addAll(errs);

            if (errs.size() == 0) {
                if (stmt instanceof RWStatement) {
                    HashSet<STentry> rwSet = ((RWStatement) stmt).getRWSet();
                    for (STentry e : rwSet) {
                        rwIDs.add(e);
                        env.containsName(e.getName());
                    }
                }
                if (stmt instanceof DeletingStatement) {
                    HashSet<STentry> delSet = ((DeletingStatement) stmt).getDelSet();
                    for (STentry e : delSet) {
                        if (env.isDeleted(e)) {
                            errors.add(new DoubleDeletionError(e.getType() + " " + e.getName()));
                            break outer;
                        } else {
                            deletedIDs.add(e);
                            env.deleteName(e.getName());
                        }
                    }
                }
            } else {
                break;
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

