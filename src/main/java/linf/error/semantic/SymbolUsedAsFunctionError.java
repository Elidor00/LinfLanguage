package linf.error.semantic;

import linf.type.LinfType;

public class SymbolUsedAsFunctionError extends SemanticError {
    private static final String msg = " used as a function, but is bound to ";
    private final String id;

    public SymbolUsedAsFunctionError(String id, LinfType type) {
        super(id + msg + type + ".");
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
