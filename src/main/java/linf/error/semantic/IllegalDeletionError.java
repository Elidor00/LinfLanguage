package linf.error.semantic;

public class IllegalDeletionError extends SemanticError {
    private final String id;

    public IllegalDeletionError(String id) {
        super("Illegal deletion statement: identifier " + id + " will be deleted two or more times.");
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
