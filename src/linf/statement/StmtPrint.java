package linf.statement;

import linf.type.LinfType;
import linf.utils.Environment;
import linf.utils.SemanticError;

import java.util.ArrayList;

public class StmtPrint extends Stmt {
    @Override
    public LinfType checkType() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<>();
    }
}
