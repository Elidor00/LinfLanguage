package linf.error.type;

import linf.expression.Exp;

public class ReferenceParameterError extends TypeError {
    public ReferenceParameterError(String id, Exp exp) {
        super(msg(id, exp));
    }

    private static String msg(String id, Exp exp) {
        return "In function " + id + ", parameter should be passed by reference but \"" + exp + "\" was found.";
    }
}
