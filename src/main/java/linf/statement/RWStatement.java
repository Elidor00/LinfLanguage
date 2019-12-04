package linf.statement;

import linf.utils.STentry;

import java.util.HashSet;

interface RWStatement extends LinfStmt {
    HashSet<STentry> getRWSet();
}
