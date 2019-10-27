package linf.error.semantic;

import linf.expression.Exp;

public class ReferenceParameterError extends SemanticError {
    private final String id;

    public ReferenceParameterError(String fun, Exp exp) {
        super("In function " + fun + ", a parameter should be passed by reference but \"" + exp + "\" was found.");
        this.id = fun;
    }

    public String getId() {
        return id;
    }
}
