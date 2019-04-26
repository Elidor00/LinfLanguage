package ast;

public class SemanticError {
    private final String msg;

    public SemanticError(String msg) {
        this.msg = "[SemanticError] " + msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}

