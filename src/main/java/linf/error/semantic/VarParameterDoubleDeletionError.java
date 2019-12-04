package linf.error.semantic;

import linf.expression.IDValue;
import linf.statement.FunCall;

public class VarParameterDoubleDeletionError extends SemanticError {
    private static final String msg = " is double deleted in function ";
    private final IDValue id;
    private final FunCall fun;


    public VarParameterDoubleDeletionError(IDValue id, FunCall f) {
        super(id.getType().toString() + " " + id + msg + f.getId());
        this.id = id;
        this.fun = f;
    }

    public IDValue getId() {
        return id;
    }

    public FunCall getFun() {
        return fun;
    }
}
