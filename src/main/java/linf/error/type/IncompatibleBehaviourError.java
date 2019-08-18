package linf.error.type;

import linf.expression.IDValue;

public class IncompatibleBehaviourError extends TypeError {
    private static final String msg = "Deletion Error: Both RW and DEL access on identifier ";

    public IncompatibleBehaviourError(IDValue id) {
        super(msg + id + ".");
    }
}
