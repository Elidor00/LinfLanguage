package linf.error.semantic;

public class FunctionNameShadowingError extends SemanticError {
    private static String msg = "cannot be used both as function identifier and formal parameter.";

    public FunctionNameShadowingError(String id) {
        super(id + msg);
    }
}
