package linf.error.type;

import linf.type.LinfType;

public class WrongParameterTypeError extends TypeError {
    private static String msg(String id, LinfType formal, LinfType actual) {
        return "In function " + id + ", " + formal + " is required but " + actual + " was found.";
    }

    public WrongParameterTypeError(String id, LinfType formal, LinfType actual) {
        super(msg(id, formal, actual));
    }
}
