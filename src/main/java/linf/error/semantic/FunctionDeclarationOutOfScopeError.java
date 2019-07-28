package linf.error.semantic;

public class FunctionDeclarationOutOfScopeError extends SemanticError {
    private static String msg = " function used before declaration.";
    private String id;

    public FunctionDeclarationOutOfScopeError(String id) {
        super(id + msg);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
