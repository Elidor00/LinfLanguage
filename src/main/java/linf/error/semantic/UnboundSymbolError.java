package linf.error.semantic;

import linf.expression.IDValue;

public class UnboundSymbolError extends SemanticError {
    private static final String msg = " identifier is not bound in current scope.";

    public UnboundSymbolError(String id) {
        super(id + msg);
    }

    public UnboundSymbolError(IDValue id) {
        super(id + msg);
    }
}
