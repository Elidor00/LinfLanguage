package linf.error.semantic;

public class WrongParameterNumberError extends SemanticError {
    private final String id;

    public WrongParameterNumberError(String id, int formal, int actual) {
        super(msg(id, formal, actual));
        this.id = id;
    }

    private static String msg(String id, int formal, int actual) {
        return "Function" + id + " requires " + formal + " parameters, but " + actual + " were given.";
    }

    public String getId() {
        return id;
    }
}
