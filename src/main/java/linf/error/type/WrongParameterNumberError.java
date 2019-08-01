package linf.error.type;

public class WrongParameterNumberError extends TypeError {
    public WrongParameterNumberError(String id, int formal, int actual) {
        super(msg(id, formal, actual));
    }

    private static String msg(String id, int formal, int actual) {
        return "Function" + id + " requires " + formal + " parameters, but " + actual + " were given.";
    }
}
