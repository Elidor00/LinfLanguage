package linf.error.semantic;


public class DoubleDeletionError extends SemanticError {
    private String id;

    public String getId() {
        return id;
    }

    public DoubleDeletionError(String id) {
        super("Double Deletion. Identifier \"" + id + "\" will be deleted two or more times.");
        this.id = id;
    }
}
