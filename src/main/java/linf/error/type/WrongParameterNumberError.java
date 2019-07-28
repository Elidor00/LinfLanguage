package linf.error.type;

public class WrongParameterNumberError extends TypeError {
    private static String msg(String id, int formal, int actual) {
        return "Function" + id + " requires " + formal + " parameters, but " + actual + " were given.";
    }

    public WrongParameterNumberError(String id, int formal, int actual) {
        super(msg(id, formal, actual));
    }
}
