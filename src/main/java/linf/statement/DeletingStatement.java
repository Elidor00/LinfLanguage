package linf.statement;

import linf.utils.STentry;

import java.util.HashSet;

interface DeletingStatement extends LinfStmt {
    HashSet<STentry> getDelSet();
}
