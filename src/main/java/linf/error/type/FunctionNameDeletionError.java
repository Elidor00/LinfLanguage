package linf.error.type;

public class FunctionNameDeletionError extends TypeError {

    public FunctionNameDeletionError(String id) {
        super("Function name deleted. The called function \"" + id + "()\" has been deleted.");
    }
}