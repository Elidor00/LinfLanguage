package linf.error.semantic;

import linf.expression.IDValue;

public class IllegalDeletionError extends SemanticError {
    private final String id;

    public IllegalDeletionError(IDValue id) {
        super("Illegal deletion statement: identifier " + id + " will be deleted two or more times.");
        this.id = id.toString();
    }

    public String getId() {
        return id;
    }
}
