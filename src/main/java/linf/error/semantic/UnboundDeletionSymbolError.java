package linf.error.semantic;

import linf.expression.IDValue;

public class UnboundDeletionSymbolError extends UnboundSymbolError {
    private static final String msg = "Cannot delete identifier before declaring it. ";
    private final String id;

    public UnboundDeletionSymbolError(IDValue id) {
        super(msg + id);
        this.id = id.toString();
    }

    public String getId() {
        return id;
    }
}
