package linf.error.semantic;


public class DoubleDeletionError extends SemanticError {

    public DoubleDeletionError(String id) {
        super("Double Deletion. Identifier \"" + id + "\" will be deleted two or more times.");
    }
}
