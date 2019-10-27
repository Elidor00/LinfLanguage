package linf.error.semantic;

public class FunctionNameDeletionError extends SemanticError {
    private final String id;

    public FunctionNameDeletionError(String id) {
        super("Illegal function deletion. " + id + " has been deleted inside its own body.");
        this.id = id;
    }

    public String getId() {
        return id;
    }
}