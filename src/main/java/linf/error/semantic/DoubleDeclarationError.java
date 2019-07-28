package linf.error.semantic;

import linf.expression.IDValue;


public class DoubleDeclarationError extends SemanticError {
    public DoubleDeclarationError(String id) {
        super("Identifier " + id + " already declared.");
    }

    public DoubleDeclarationError(IDValue id) {
        super("Identifier " + id + " already declared.");
    }
}