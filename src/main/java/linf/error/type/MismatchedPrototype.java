package linf.error.type;

import linf.type.FunType;

public class MismatchedPrototype extends TypeError {
    public MismatchedPrototype(String id, FunType expected, FunType actual) {
        super(msg(id, expected, actual));
    }

    private static String msg(String id, FunType expected, FunType actual) {
        return "Function " + id + " declaration requires " + expected + " type, but " + actual + " was found.";
    }
}
