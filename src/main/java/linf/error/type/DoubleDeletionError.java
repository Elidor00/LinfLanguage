package linf.error.type;

import linf.expression.IDValue;

public class DoubleDeletionError extends TypeError {

    public DoubleDeletionError(IDValue id) {
        super("Double Deletion. Identifier \"" + id + "\" will be deleted two or more times.");
    }
}
