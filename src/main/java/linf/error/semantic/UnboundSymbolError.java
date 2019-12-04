package linf.error.semantic;

import linf.expression.IDValue;

public class UnboundSymbolError extends SemanticError {
    private static final String msg = " identifier is not bound in current scope.";
    private final String id;

    public UnboundSymbolError(String id) {
        super(id + msg);
        this.id = id;
    }

    public UnboundSymbolError(IDValue id) {
        super(id + msg);
        this.id = id.toString();
    }

    public String getId() {
        return id;
    }
}
